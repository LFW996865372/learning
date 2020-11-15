package com.cgb.luofenwu.spring.framework.beans.config;

/**
 *@Author:luofenwu
 *@Date: 2020/11/15 18:31
 *@Description:
 */
public class LfwBeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }
}
