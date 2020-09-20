package com.cgb.luofenwu.proxy.jdkproxy.myimpl;

import java.lang.reflect.Method;

/**
 * @Autor:LFW
 * @Description:自己做的JDK反射的使用
 * @Date:create in 2020/9/170:25
 */
public class MyUserControllerProxy implements MyInvocationHandler {
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
        return MyProxy.newProxyInstance(new MyClazzLoader(), clazz.getInterfaces(), this);
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
        System.out.println("走自己实现的 my jdk proxy before");
    }

    private void after() {
        System.out.println("走自己实现的 my jdk proxy after");
    }
}
