package com.cgb.luofenwu.proxy.statiz.extendsrc;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/1523:47
 */
public class UserControllerProxy extends UserController {

    public void login() {
        LogService.check("login");
        super.login();
        LogService.saveLog("login");
    }

    public void logout() {
        LogService.check("logout");
        super.logout();
        LogService.saveLog("logout");
    }

    public void register() {
        LogService.check("register");
        super.register();
        LogService.saveLog("register");
    }
}
