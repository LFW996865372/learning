package com.gupaoedu.vip.pattern.singleton.seriable;

import java.io.Serializable;

/**
 * 反序列化时导致单例破坏
 * Created by Tom.
 */
public class SeriableSingleton implements Serializable {

    /**
     * 序列化就是说把内存中的状态通过转换成字节码的形式
     * 从而转换一个IO流，写入到其他地方(可以是磁盘、网络IO)
     * 内存中状态给永久保存下来了
     * <p>
     * 反序列化
     * 将已经持久化的字节码内容，转换为IO流
     * 通过IO流的读取，进而将读取的内容转换为Java对象
     * 在转换过程中会重新创建对象new
     */
    public final static SeriableSingleton INSTANCE = new SeriableSingleton();

    private SeriableSingleton() {
    }

    public static SeriableSingleton getInstance() {
        return INSTANCE;
    }

    /**
     * 重写readResolve方法，只不过是覆盖了反序列话生成的对象
     * 发生在JVM层面还是执行了两次创建，相对来说比较安全
     * 之前反序列化出来的对象会被GC回收
     *
     * 为何这样设计？
     * 避免序列化导致的单例破坏（JDK的设计者考虑到了反射）
     * 实质上会先执行反序列化，再使用readResolve的值进行覆盖;
     * @return
     */
    private Object readResolve() {
        return INSTANCE;
    }

}
