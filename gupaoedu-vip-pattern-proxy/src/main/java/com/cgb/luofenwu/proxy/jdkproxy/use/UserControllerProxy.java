package com.cgb.luofenwu.proxy.jdkproxy.use;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/170:25
 */
public class UserControllerProxy implements InvocationHandler {
    /**
     * 被代理对象
     */
    private Object target;

    /**
     * 为target创建一个代理
     *
     * @param target
     * @return
     * @throws Exception
     */
    public Object getInstance(Object target) throws Exception {
        this.target = target;
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    /**
     * 需要执行代理的逻辑
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object obj = method.invoke(this.target, args);
        after();
        return obj;
    }

    private void before() {
        System.out.println("jdk proxy before");
    }

    private void after() {
        System.out.println("jdk proxy after");

    }
}
