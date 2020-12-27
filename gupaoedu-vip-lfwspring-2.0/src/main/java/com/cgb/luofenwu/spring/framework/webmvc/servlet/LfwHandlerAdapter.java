package com.cgb.luofenwu.spring.framework.webmvc.servlet;

import com.cgb.luofenwu.spring.framework.annotation.LfwRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理适配器
 */
public class LfwHandlerAdapter {

    public LfwModelAndView handler(HttpServletRequest req, HttpServletResponse resp, LfwHandlerMapping handler) throws Exception {
        //保存形参列表
        //将参数名称和参数的位置的关系保存起来
        Map<String, Integer> paramIndexMapping = new HashMap<String, Integer>();
        Annotation[][] pa = handler.getMethod().getParameterAnnotations();
        for (int i = 0; i < pa.length; i++) {
            for (Annotation a : pa[i]) {
                if (a instanceof LfwRequestParam) {
                    String paramName = ((LfwRequestParam) a).value();
                    if (!"".equals(paramName.trim())) {
                        paramIndexMapping.put(paramName, i);
                    }
                }
            }
        }

        //参数类型
        Class<?>[] paramTypes = handler.getMethod().getParameterTypes();
        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> paramterType = paramTypes[i];
            if (paramterType == HttpServletRequest.class ||
                    paramterType == HttpServletResponse.class) {
                paramIndexMapping.put(paramterType.getName(), i);
            }
        }

        //拼接实参列表  //http://localhost/web/query?name=Tom&Cat
        Map<String, String[]> params = req.getParameterMap();
        Object[] paramValues = new Object[paramTypes.length];
        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String value = Arrays.toString(params.get(param.getKey()))
                    .replaceAll("\\[|\\]", "")
                    .replaceAll("\\s+", ",");
            if (!paramIndexMapping.containsKey(param.getKey())) {
                continue;
            }
            int index = paramIndexMapping.get(param.getKey());
            //允许自定义的类型转换器Converter
            paramValues[index] = castStringValue(value, paramTypes[index]);
        }

        //特殊处理HttpServletRequest、HttpServletResponse
        if (paramIndexMapping.containsKey(HttpServletRequest.class.getName())) {
            int index = paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[index] = req;
        }
        if (paramIndexMapping.containsKey(HttpServletResponse.class.getName())) {
            int index = paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[index] = resp;
        }

        //反射调用
        Object result = handler.getMethod().invoke(handler.getController(), paramValues);
        if (result == null || result instanceof Void) {
            return null;
        }

        //返回ModelAndView
        boolean isModelAndView = handler.getMethod().getReturnType() == LfwModelAndView.class;
        if (isModelAndView) {
            return (LfwModelAndView) result;
        }
        return null;
    }

    /**
     * 可以策略分开
     *
     * @param value
     * @param paramType
     * @return
     */
    private Object castStringValue(String value, Class<?> paramType) {
        if (String.class == paramType) {
            return value;
        } else if (Integer.class == paramType) {
            return Integer.valueOf(value);
        } else if (Double.class == paramType) {
            return Double.valueOf(value);
        } else {
            if (value != null) {
                return value;
            }
            return null;
        }

    }
}
