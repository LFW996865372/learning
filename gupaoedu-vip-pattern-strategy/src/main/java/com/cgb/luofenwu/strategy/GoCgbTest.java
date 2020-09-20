package com.cgb.luofenwu.strategy;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/2019:30
 */
public class GoCgbTest {
    public static void main(String[] args) {
        IHowToGo howToGo = GoStrategy.get("公交车");
        howToGo.go();
    }
}
