package com.cgb.luofenwu.mvcframwork.servlet;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/10/2518:19
 */

import com.gupaoedu.mvcframework.annotation.GPRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Author:luofenwu
 * @Date: 2020/10/25 18:20
 * @Description: Handler记录Controller中的RequestMapping和Method的对应关系
 */
public class Handler {
    protected Object controller;    //保存方法对应的实例
    protected Method method;        //保存映射的方法
    protected String url;//使用pattern可以处理/*的情况
    protected Pattern pattern;//使用pattern可以处理/*的情况

    public Object getController() {
        return controller;
    }

    public Method getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Map<String, Integer> getParamIndexMapping() {
        return paramIndexMapping;
    }

    protected Map<String, Integer> paramIndexMapping;    //参数顺序

    /**
     * 构造一个Handler基本的参数
     *
     * @param controller
     * @param method
     */
    protected Handler(Pattern pattern, Object controller, Method method) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
        paramIndexMapping = new HashMap<String, Integer>();
        putParamIndexMapping(method);
    }

    private void putParamIndexMapping(Method method) {
        //提取方法中加了注解的参数
        Annotation[][] pa = method.getParameterAnnotations();
        for (int i = 0; i < pa.length; i++) {
            for (Annotation a : pa[i]) {
                if (a instanceof GPRequestParam) {
                    String paramName = ((GPRequestParam) a).value();
                    if (!"".equals(paramName.trim())) {
                        paramIndexMapping.put(paramName, i);
                    }
                }
            }
        }

        //提取方法中的request和response参数
        Class<?>[] paramsTypes = method.getParameterTypes();
        for (int i = 0; i < paramsTypes.length; i++) {
            Class<?> type = paramsTypes[i];
            if (type == HttpServletRequest.class ||
                    type == HttpServletResponse.class) {
                paramIndexMapping.put(type.getName(), i);
            }
        }
    }
}
