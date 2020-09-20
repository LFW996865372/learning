package com.cgb.luofenwu.proxy.statiz.implsameinterface;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/1523:47
 */
public class UserControllerProxy implements IUserController {
    private UserController userController;

    public UserControllerProxy(UserController userController) {
        this.userController = userController;
    }

    public void login() {
        LogService.check("login");
        userController.login();
        LogService.saveLog("login");
    }

    public void logout() {
        LogService.check("logout");
        userController.logout();
        LogService.saveLog("logout");
    }

    public void register() {
        LogService.check("register");
        userController.register();
        LogService.saveLog("register");
    }
}
