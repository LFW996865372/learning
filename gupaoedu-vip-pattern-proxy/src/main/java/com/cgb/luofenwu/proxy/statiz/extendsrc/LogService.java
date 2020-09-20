package com.cgb.luofenwu.proxy.statiz.extendsrc;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @Autor:LFW
 * @Description:记录日志
 * @Date:create in 2020/9/1523:49
 */
public class LogService {

    public static void check(String action) {
        System.out.println(action + "是否允许执行");
        if(action.equals("login")){
            Random random=new SecureRandom();
            if(random.nextBoolean()){
                throw new RuntimeException("now ,u r the one");
            }
        }
    }

    public static void saveLog(String action) {
        System.out.println(action + "已写入日志");
    }
}
