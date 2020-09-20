package com.cgb.luofenwu.proxy.statiz.implsameinterface;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/1523:57
 */
public class UserControllerProxyTest {
    public static void main(String[] args) {
        //原始类执行
        UserController userController = new UserController();
        userController.login();
        userController.logout();
        userController.register();

        //代理类执行
        UserControllerProxy userControllerProxy = new UserControllerProxy(userController);
        userControllerProxy.login();
        userControllerProxy.logout();
        userControllerProxy.register();
    }
}
