package com.cgb.luofenwu.pattern.factory.abstractfactory;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/1321:24
 */
public class JsonConfigParseFactory implements IConfigParseFactory {

    public IRuleParse createRuleParse() {
        return new JsonRuleParse();
    }

    public ISysParse createSysParse() {
        return new JsonSysParse();
    }
}
