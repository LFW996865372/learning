package com.cgb.luofenwu.spring.framework.core;

/**
 * Spring容器的顶层接口
 * 单例工厂的顶层设计
 * @Author LFW.
 */
public interface LfwBeanFactory {
    /**
     * 根据beanName从IOC容器中获得一个实例Bean
     * @param beanName
     * @return
     * @throws Exception
     */
    Object getBean(String beanName) throws Exception;

    /**
     * 根据class类型从IOC容器中获得一个实例Bean
     * @param beanClass
     * @return
     * @throws Exception
     */
     Object getBean(Class<?> beanClass) throws Exception;

}
