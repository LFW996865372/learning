package com.cgb.luofenwu.pattern.adapter;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/2721:56
 */
public class ToothbrushAdapterTest {
    public static void main(String[] args) {
        ToothbrushCharger toothbrushCharger=new ToothbrushCharger(new RazorCharger());
        toothbrushCharger.charge();
    }
}
