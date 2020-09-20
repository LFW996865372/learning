package com.cgb.luofenwu.proxy.jdkproxy.myimpl;


/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/170:40
 */
public class MyProxyTest {
    public static void main(String[] args) {
        try {
            IUserController iUserController = (IUserController) new MyUserControllerProxy().getInstance(new MyUserController());
            iUserController.login();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
