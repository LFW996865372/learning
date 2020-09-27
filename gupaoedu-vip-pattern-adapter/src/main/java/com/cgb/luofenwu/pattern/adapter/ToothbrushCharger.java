package com.cgb.luofenwu.pattern.adapter;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/2721:44
 */
public class ToothbrushCharger implements IChargeAdapter {
    private ICharger charger;

    public ToothbrushCharger(ICharger charger) {
        this.charger = charger;
    }

    public void charge() {
        charger.charger();
        System.out.println("ToothbrushCharger只要再加个适配器:牙刷充电器底座");
    }
}
