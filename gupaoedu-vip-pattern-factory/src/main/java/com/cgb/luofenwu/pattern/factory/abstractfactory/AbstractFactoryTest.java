package com.cgb.luofenwu.pattern.factory.abstractfactory;

import com.gupaoedu.vip.pattern.factory.abstractfactory.JavaCourseFactory;

/**
 * Created by Tom.
 */
public class AbstractFactoryTest {

    public static void main(String[] args) {
        JsonConfigParseFactory factory = new JsonConfigParseFactory();
        factory.createRuleParse().parse();
        factory.createSysParse().parse();
    }

}
