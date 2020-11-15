package com.cgb.luofenwu.spring.framework.context.suport;

/**
 * IOC容器实现的顶层设计
 *
 * @Author LFW.
 */
public abstract class LfwAbstractApplicationContext {
    /**
     * 一键启动的refresh
     * 受保护，只提供给子类重写
     *
     * @throws Exception
     */
    protected void refresh() throws Exception {
    }
}
