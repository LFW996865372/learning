package com.cgb.luofenwu.proxy.statiz.implsameinterface;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/1523:42
 */
public class UserController implements IUserController {

    public void login() {
        System.out.println("login");
    }

    public void logout() {
        System.out.println("logout");
    }

    public void register() {
        System.out.println("register");

    }
}
