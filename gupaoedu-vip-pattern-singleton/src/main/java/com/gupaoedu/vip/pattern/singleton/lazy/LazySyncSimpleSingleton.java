package com.gupaoedu.vip.pattern.singleton.lazy;

/**
 * Created by Tom.
 * update by luofenwu
 * 懒汉式单例-同步锁版
 * 优点：相对的优点（比LazySimpleSingleton而言），线程安全
 * 缺点:造成过多的同步开销。每次访问都要进行线程同步，加锁，耗时耗能
 */
public class LazySyncSimpleSingleton {
    private LazySyncSimpleSingleton() {
    }

    //静态块，公共内存区域
    private static LazySyncSimpleSingleton lazy = null;

    /**
     * getInstance()方法是static修饰的，synchronized是类锁，可能造成整个类都被锁定
     *
     * @return
     */
    public synchronized static LazySyncSimpleSingleton getInstance() {
        if (lazy == null) {
            lazy = new LazySyncSimpleSingleton();
        }
        return lazy;
    }
}
