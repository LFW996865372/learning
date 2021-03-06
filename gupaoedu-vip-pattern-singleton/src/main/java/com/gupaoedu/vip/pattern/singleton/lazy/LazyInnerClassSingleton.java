package com.gupaoedu.vip.pattern.singleton.lazy;

/**
 * Created by Tom.
 * update by luofenwu
 * 懒汉式单例-静态内部类
 * 兼顾饿汉式的内存浪费，也兼顾synchronized性能问题（完美地屏蔽了这两个缺点）
 * 史上最牛B的单例模式的实现方式
 */
public class LazyInnerClassSingleton {
    //默认使用LazyInnerClassGeneral的时候，会先初始化内部类
    //重点：如果没使用的话，内部类是不加载的
    private LazyInnerClassSingleton() {
        if (LazyHolder.LAZY != null) {
            //防止暴力反射
            throw new RuntimeException("不允许创建多个实例");
        }
    }
    /**
     * 每一个关键字都不是多余的
     * static 是为了使单例的空间共享
     * final 保证这个方法不会被重写
     * @return
     */
    public static final LazyInnerClassSingleton getInstance() {
        //在返回结果以前，一定会先加载内部类
        return LazyHolder.LAZY;
    }
    //静态内部类默认不加载
    private static class LazyHolder {
        private static final LazyInnerClassSingleton LAZY = new LazyInnerClassSingleton();
    }
}
