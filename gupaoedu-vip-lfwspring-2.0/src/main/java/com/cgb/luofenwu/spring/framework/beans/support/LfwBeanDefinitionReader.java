package com.cgb.luofenwu.spring.framework.beans.support;


import com.cgb.luofenwu.spring.framework.beans.config.LfwBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 加载定位（读取配置文件+扫描）
 *
 * @Author LFW.
 */
public class LfwBeanDefinitionReader {

    private List<String> registyBeanClasses = new ArrayList<String>();

    private Properties config = new Properties();

    //固定配置文件中的key，相对于xml的规范
    private final String SCAN_PACKAGE = "scanPackage";

    /**
     * @param locations
     */
    public LfwBeanDefinitionReader(String... locations) {
        //通过URL定位找到其所对应的文件，然后转换为文件流
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:", ""))) {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    /**
     * 抄1.0
     * 扫码指定包的类
     *
     * @param scanPackage
     */
    private void doScanner(String scanPackage) {
        //转换为文件路径，实际上就是把.替换为/就OK了
        URL url = this.getClass().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = (scanPackage + "." + file.getName().replace(".class", ""));
                registyBeanClasses.add(className);
            }
        }
    }

    public Properties getConfig() {
        return this.config;
    }

    /**
     * 把配置文件中扫描到的所有的配置信息转换为BeanDefinition对象，以便于之后IOC操作方便
     * @return
     */
    public List<LfwBeanDefinition> loadBeanDefinitions() {
        List<LfwBeanDefinition> result = new ArrayList<LfwBeanDefinition>();
        try {
            for (String className : registyBeanClasses) {
                Class<?> beanClass = Class.forName(className);
                //如果是一个接口，是不能实例化的
                //用它实现类来实例化
                if (beanClass.isInterface()) {
                    continue;
                }

                //beanName有三种情况:
                //1、默认是类名首字母小写
                //2、自定义名字
                //3、接口注入
                result.add(doCreateBeanDefinition(toLowerFirstCase(beanClass.getSimpleName()), beanClass.getName()));

                Class<?>[] interfaces = beanClass.getInterfaces();
                for (Class<?> i : interfaces) {
                    //如果是多个实现类，只能覆盖，可以自定义名字
                    result.add(doCreateBeanDefinition(i.getName(), beanClass.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 把每一个配信息解析成一个BeanDefinition
     *
     * @param factoryBeanName
     * @param beanClassName
     * @return
     */
    private LfwBeanDefinition doCreateBeanDefinition(String factoryBeanName, String beanClassName) {
        LfwBeanDefinition beanDefinition = new LfwBeanDefinition();
        beanDefinition.setBeanClassName(beanClassName);
        beanDefinition.setFactoryBeanName(factoryBeanName);
        return beanDefinition;
    }

    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        //之所以加，是因为大小写字母的ASCII码相差32，
        // 而且大写字母的ASCII码要小于小写字母的ASCII码
        //在Java中，对char做算学运算，实际上就是对ASCII码做算学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
