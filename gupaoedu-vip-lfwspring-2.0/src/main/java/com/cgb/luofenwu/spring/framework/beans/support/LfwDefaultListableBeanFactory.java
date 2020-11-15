package com.cgb.luofenwu.spring.framework.beans.support;


import com.cgb.luofenwu.spring.framework.beans.config.LfwBeanDefinition;
import com.cgb.luofenwu.spring.framework.context.suport.LfwAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanFactory默认实现
 *
 * @Author LFW
 */
public class LfwDefaultListableBeanFactory extends LfwAbstractApplicationContext {

    /**
     * 存储注册信息的BeanDefinition
     */
    protected final Map<String, LfwBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, LfwBeanDefinition>();
}
