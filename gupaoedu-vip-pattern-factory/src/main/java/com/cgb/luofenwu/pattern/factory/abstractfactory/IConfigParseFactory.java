package com.cgb.luofenwu.pattern.factory.abstractfactory;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/1321:21
 */
public interface IConfigParseFactory {
    /**
     * 创建业务规则解析器
     * @return
     */
    IRuleParse createRuleParse();

    /**
     * 系统初始化规则解析器
     * @return
     */
    ISysParse createSysParse();
}
