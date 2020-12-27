package com.cgb.luofenwu.spring.framework.context;


import com.cgb.luofenwu.spring.framework.annotation.LfwAutowired;
import com.cgb.luofenwu.spring.framework.annotation.LfwController;
import com.cgb.luofenwu.spring.framework.annotation.LfwService;
import com.cgb.luofenwu.spring.framework.beans.LfwBeanWrapper;
import com.cgb.luofenwu.spring.framework.beans.config.LfwBeanDefinition;
import com.cgb.luofenwu.spring.framework.beans.config.LfwBeanPostProcessor;
import com.cgb.luofenwu.spring.framework.beans.support.LfwBeanDefinitionReader;
import com.cgb.luofenwu.spring.framework.beans.support.LfwDefaultListableBeanFactory;
import com.cgb.luofenwu.spring.framework.core.LfwBeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IOC容器
 *
 * @Author LFW.
 */
public class LfwApplicationContext extends LfwDefaultListableBeanFactory implements LfwBeanFactory {

    private String[] configLoactions;
    private LfwBeanDefinitionReader reader;
    //单例的IOC容器缓存
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();
    //通用的IOC容器
    private Map<String, LfwBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<String, LfwBeanWrapper>();

    public LfwApplicationContext(String... configLoactions) {
        this.configLoactions = configLoactions;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() throws Exception {
        //1、定位，定位配置文件
        reader = new LfwBeanDefinitionReader(this.configLoactions);

        //2、加载配置文件，扫描相关的类，把它们封装成BeanDefinition
        List<LfwBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        //3、注册，把配置信息放到容器里面(伪IOC容器)
        doRegisterBeanDefinition(beanDefinitions);

        //4、把不是延时加载的类，提前初始化
        doAutowrited();
    }

    //只处理非延时加载的情况
    private void doAutowrited() {
        for (Map.Entry<String, LfwBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()) {
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 注册，把配置信息放到容器里面(伪IOC容器)
     *
     * @param beanDefinitions
     * @throws Exception
     */
    private void doRegisterBeanDefinition(List<LfwBeanDefinition> beanDefinitions) throws Exception {
        for (LfwBeanDefinition beanDefinition : beanDefinitions) {
            if (super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())) {
                throw new Exception("The “" + beanDefinition.getFactoryBeanName() + "” is exists!!");
            }
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
        //到这里为止，容器初始化完毕
    }

    public Object getBean(Class<?> beanClass) throws Exception {
        return getBean(beanClass.getName());
    }

    /**
     * 老师的注释:
     * 依赖注入，从这里开始，通过读取BeanDefinition中的信息
     * Spring做法是，不会把最原始的对象放出去，会用一个BeanWrapper来进行一次包装
     * 装饰器模式：
     * 1、保留原来的OOP关系
     * 2、我需要对它进行扩展，增强（为了以后AOP打基础）
     *
     * @param beanName
     * @return
     * @throws Exception
     */
    public Object getBean(String beanName) throws Exception {
        //1、先拿到BeanDefinition配置信息
        LfwBeanDefinition lfwBeanDefinition = this.beanDefinitionMap.get(beanName);
        Object instance = null;

        //TODO 这个逻辑不严谨，去参考Spring源码
        //工厂模式 + 策略模式
        //Bean初始化前的钩子
        LfwBeanPostProcessor postProcessor = new LfwBeanPostProcessor();
        postProcessor.postProcessBeforeInitialization(instance, beanName);
        //NOTE 为何要先初始化再注入，而不能初始化同时完成注入-->循环依赖
        //class A{ B b;}
        //class B{ A a;}
        //先有鸡还是先有蛋的问题，一个方法是搞不定的，要分两次

        //2、反射式、实例化bean
        instance = instantiateBean(lfwBeanDefinition);

        //3、把这个对象封装到BeanWrapper中
        LfwBeanWrapper beanWrapper = new LfwBeanWrapper(instance);

        //4、拿到BeanWrapper之后，把BeanWrapper保存到IOC容器中去
        this.factoryBeanInstanceCache.put(beanName, beanWrapper);
        //Bean初始化后的钩子
        postProcessor.postProcessAfterInitialization(instance, beanName);


        //可能涉及到循环依赖？
        //A{ B b}
        //B{ A b}
        //用两个缓存，循环两次
        //1、把第一次读取结果为空的BeanDefinition存到第一个缓存
        //2、等第一次循环之后，第二次循环再检查第一次的缓存，再进行赋值
        //5、执行依赖注入
        populateBean(beanName, new LfwBeanDefinition(), beanWrapper);
        return beanWrapper.getWrappedInstance();
    }

    /**
     * 注入bean
     *
     * @param beanName
     * @param lfwBeanDefinition
     * @param lfwBeanWrapper
     */
    private void populateBean(String beanName, LfwBeanDefinition lfwBeanDefinition, LfwBeanWrapper lfwBeanWrapper) {
        Object instance = lfwBeanWrapper.getWrappedInstance();
        Class<?> clazz = lfwBeanWrapper.getWrappedClass();
        //判断只有加了注解的类，才执行依赖注入
        if (!(clazz.isAnnotationPresent(LfwController.class) || clazz.isAnnotationPresent(LfwService.class))) {
            return;
        }

        //获得所有的fields
        Field[] fields = clazz.getDeclaredFields();
        //遍历有LfwAutowired注解的属性
        for (Field field : fields) {
            if (!field.isAnnotationPresent(LfwAutowired.class)) {
                continue;
            }

            LfwAutowired autowired = field.getAnnotation(LfwAutowired.class);
            String autowiredBeanName = autowired.value().trim();
            if ("".equals(autowiredBeanName)) {
                //获取字段的类型
                autowiredBeanName = field.getType().getName();
            }

            //强制访问
            field.setAccessible(true);
            try {
                if (this.factoryBeanInstanceCache.get(autowiredBeanName) == null) {
                    continue;
                }
                field.set(instance, this.factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 反射实例化
     *
     * @param lfwBeanDefinition
     * @return
     */
    private Object instantiateBean(LfwBeanDefinition lfwBeanDefinition) {
        //1、拿到要实例化的对象的类名
        String className = lfwBeanDefinition.getBeanClassName();

        //2、反射实例化，得到一个对象
        Object instance = null;
        try {
            //lfwBeanDefinition.getFactoryBeanName()
            //假设默认就是单例,细节暂且不考虑，先把主线拉通
            if (this.singletonObjects.containsKey(className)) {
                instance = this.singletonObjects.get(className);
            } else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                this.singletonObjects.put(className, instance);
                this.singletonObjects.put(lfwBeanDefinition.getFactoryBeanName(), instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    public Properties getConfig() {
        return this.reader.getConfig();
    }
}
