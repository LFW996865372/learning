package com.gupaoedu.vip.pattern.proxy.dynamicproxy.jdkproxy;

import com.gupaoedu.vip.pattern.proxy.Person;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.Method;

/**
 * Created by Tom on 2019/3/10.
 */
public class JDKProxyTest {

    public static void main(String[] args) {
        try {
            testTrue();
//            testWhyNeedInterface();
            //$Proxy0
//            byte [] bytes = ProxyGenerator.generateProxyClass("$Proxy0",new Class[]{Person.class});
//            FileOutputStream os = new FileOutputStream("E://$Proxy0.class");
//            os.write(bytes);
//            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 正确使用的范例
     *
     * @throws Exception
     */
    public static void testTrue() throws Exception {
        Person obj = (Person) new JDKMeipo().getInstance(new Girl());
        obj.findLove();
    }

    /**
     * 为何要接口
     *
     * @throws Exception
     */
    public static void testWhyNeedInterface() throws Exception {
        Object obj = new JDKMeipo().getInstance(new Girl());
        Method method = obj.getClass().getMethod("findLove", null);
        method.invoke(obj);
    }
}
