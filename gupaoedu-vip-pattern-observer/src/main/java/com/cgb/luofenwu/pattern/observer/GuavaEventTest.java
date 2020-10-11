package com.cgb.luofenwu.pattern.observer;

import com.google.common.eventbus.EventBus;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/10/1119:17
 */
public class GuavaEventTest {
    public static void main(String[] args) {
        Question question = new Question();
        question.setUserName("luofenwu");
        question.setContent("TOM老师是不是客家人?");
        GuavaEvent guavaEvent = new GuavaEvent("tom");
        EventBus eventBus = new EventBus();
        eventBus.register(guavaEvent);
        eventBus.post(question);
    }
}
