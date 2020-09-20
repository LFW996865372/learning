package com.gupaoedu.vip.pattern.singleton.lazy;

/**
 * Created by Tom.
 * update by lufenwu
 * 懒汉式单例，双重检查锁
 */
public class LazyDoubleCheckSingleton {
    private volatile static LazyDoubleCheckSingleton lazy = null;

    private LazyDoubleCheckSingleton() {
    }

    public static LazyDoubleCheckSingleton getInstance() {
        if (lazy == null) {//为了创建单例后，后续访问不再进入synchronized代码块
            synchronized (LazyDoubleCheckSingleton.class) {
                if (lazy == null) {//二次检验，避免多线程下二次执行new，出现覆盖
                    lazy = new LazyDoubleCheckSingleton();
                    //1.分配内存给这个对象
                    //2.初始化对象
                    //3.设置lazy指向刚分配的内存地址
                    //4.初次访问对象
                }
            }
        }
        return lazy;
    }
}
