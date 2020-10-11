package com.cgb.luofenwu.pattern.observer;

import com.google.common.eventbus.Subscribe;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/10/1119:14
 */
public class GuavaEvent {
    private String name;

    public GuavaEvent(String name) {
        this.name = name;
    }

    @Subscribe
    public void subscribe(Question question) {
        System.out.println("===============================");
        System.out.println(name + "老师，你好！\n" +
                "您收到了一个来自“" + question.getUserName() + "”的提问，希望您解答，问题内容如下：\n" +
                question.getContent());
    }
}
