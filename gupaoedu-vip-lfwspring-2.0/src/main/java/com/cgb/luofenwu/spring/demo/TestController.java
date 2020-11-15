package com.cgb.luofenwu.spring.demo;

import com.cgb.luofenwu.spring.framework.annotation.LfwAutowired;
import com.cgb.luofenwu.spring.framework.annotation.LfwController;


/**
 * 公布接口url
 *
 * @Author LFW
 */
@LfwController
public class TestController {

    @LfwAutowired
    IQueryService queryService;

}
