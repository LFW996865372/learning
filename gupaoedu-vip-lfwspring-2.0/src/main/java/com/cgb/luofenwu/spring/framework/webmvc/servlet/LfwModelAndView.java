package com.cgb.luofenwu.spring.framework.webmvc.servlet;

import java.util.Map;

public class LfwModelAndView {
    private String viewName;
    private Map<String, ?> model;

    public LfwModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public LfwModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }
}


