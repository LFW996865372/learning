package com.cgb.luofenwu.proxy.jdkproxy.myimpl;

import java.lang.reflect.Method;

/**
 * @Autor:LFW
 * @Description:自定义InvocationHandler接口
 * @Date:create in 2020/9/2020:03
 */
public interface MyInvocationHandler {
    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
