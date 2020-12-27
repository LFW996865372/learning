package com.cgb.luofenwu.spring.framework.webmvc.servlet;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * 封装路径对应的控制器
 * @Author:luofenwu
 * @Description:
 */
public class LfwHandlerMapping {
    /**
     * URL
     */
    private Pattern pattern;
    private Method method;
    /**
     * Method对应的实例对象
     */
    private Object controller;

    public LfwHandlerMapping(Pattern pattern, Object controller, Method method) {
        this.pattern = pattern;
        this.method = method;
        this.controller = controller;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    @Override
    public String toString() {
        return "LfwHandlerMapping{" +
                "pattern=" + pattern +
                ", method=" + method +
                ", controller=" + controller +
                '}';
    }
}
