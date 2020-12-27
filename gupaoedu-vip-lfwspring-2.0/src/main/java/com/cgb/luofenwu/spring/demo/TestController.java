package com.cgb.luofenwu.spring.demo;

import com.cgb.luofenwu.spring.framework.annotation.LfwAutowired;
import com.cgb.luofenwu.spring.framework.annotation.LfwController;
import com.cgb.luofenwu.spring.framework.annotation.LfwRequestMapping;
import com.cgb.luofenwu.spring.framework.annotation.LfwRequestParam;
import com.cgb.luofenwu.spring.framework.webmvc.servlet.LfwModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 测试
 *
 * @Author LFW
 */
@LfwController
@LfwRequestMapping("/test")
public class TestController {

    @LfwAutowired
    IQueryService queryService;

    //jetty 启动 http://localhost:80/test/query
    @LfwRequestMapping("/query")
    public LfwModelAndView query(HttpServletRequest request, HttpServletResponse response,
                                 @LfwRequestParam("name") String name) {
        String result = queryService.query(name);
        return out(response, result);
    }


    private LfwModelAndView out(HttpServletResponse resp, String str) {
        try {
            resp.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
