package com.cgb.luofenwu.proxy.jdkproxy.use;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/170:40
 */
public class JdkProxyTest {
    public static void main(String[] args) {
        try {
            IUserController iUserController = (IUserController) new UserControllerProxy().getInstance(new UserController());
            iUserController.login();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
