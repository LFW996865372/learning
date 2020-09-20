package com.cgb.luofenwu.pattern.factory.factorymetchod;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/1319:19
 */
public class FactoryMethodTest {
    public static void main(String[] args) {
        IParseFactory parseFactory = new JsonParseFactory();
        parseFactory.createParse();

        IParseFactory parseFactory2 = new XmlParseFactory();
        parseFactory.createParse();
    }
}
