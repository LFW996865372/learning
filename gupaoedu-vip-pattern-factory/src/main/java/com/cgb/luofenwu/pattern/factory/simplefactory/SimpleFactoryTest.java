package com.cgb.luofenwu.pattern.factory.simplefactory;

import com.cgb.luofenwu.pattern.factory.IParse;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/1319:22
 */
public class SimpleFactoryTest {
    public static void main(String[] args) {
        SimpleParseFactory simpleParseFactory = new SimpleParseFactory();
        IParse jsonParse = simpleParseFactory.createParse("json");

        SimpleParseFactoryWithMap simpleParseFactoryWithMap = new SimpleParseFactoryWithMap();
        IParse jsonParse2 = simpleParseFactory.createParse("xml");
    }
}
