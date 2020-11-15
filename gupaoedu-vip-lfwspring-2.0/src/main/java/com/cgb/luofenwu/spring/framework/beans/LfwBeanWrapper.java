package com.cgb.luofenwu.spring.framework.beans;

/**
 * IOC中真实存放的对象
 * 包装着bean实例的对象
 *
 * @Author LFW.
 */
public class LfwBeanWrapper {

    private Object wrappedInstance;

    private Class<?> wrappedClass;

    public LfwBeanWrapper(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    public Object getWrappedInstance() {
        return this.wrappedInstance;
    }

    // 返回代理以后的Class
    // 可能会是这个 $Proxy0
    public Class<?> getWrappedClass() {
        return this.wrappedInstance.getClass();
    }
}
