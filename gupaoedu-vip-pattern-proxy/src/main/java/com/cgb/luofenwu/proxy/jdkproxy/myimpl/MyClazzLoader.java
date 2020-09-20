package com.cgb.luofenwu.proxy.jdkproxy.myimpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * @Autor:LFW
 * @Description:自定义的类加载器
 * @Date:create in 2020/9/2019:58
 */
public class MyClazzLoader extends ClassLoader {
    private File classPathFile;

    public MyClazzLoader() {
        String classPath = MyClazzLoader.class.getResource("").getPath();
        this.classPathFile = new File(classPath);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = MyClazzLoader.class.getPackage().getName() + "." + name;
        if (classPathFile != null) {
            //创建一个class
            File classFile = new File(classPathFile, name.replaceAll("\\.", "/") + ".class");
            if (classFile.exists()) {
                try (FileInputStream in = new FileInputStream(classFile);
                     ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                    byte[] buff = new byte[1024];
                    int len;
                    while ((len = in.read(buff)) != -1) {
                        out.write(buff, 0, len);
                    }
                    return defineClass(className, out.toByteArray(), 0, out.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
