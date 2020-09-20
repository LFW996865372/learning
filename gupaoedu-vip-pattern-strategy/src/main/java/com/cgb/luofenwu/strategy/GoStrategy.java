package com.cgb.luofenwu.strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/2019:42
 */
public class GoStrategy {
    public static final String BIKE = "单车";
    public static final String BUS = "公交车";
    public static final String DD = "滴滴";
    public static final String WAIK = "走路";

    private static Map<String, IHowToGo> payStrategy = new HashMap<String, IHowToGo>();

    static {
        payStrategy.put(BIKE, new BikeToCgb());
        payStrategy.put(BUS, new BusToCgb());
        payStrategy.put(DD, new DdCarToCgb());
        payStrategy.put(WAIK, new WalkToCgb());
    }

    public static IHowToGo get(String payKey) {
        if (!payStrategy.containsKey(payKey)) {
            return payStrategy.get(BIKE);
        }
        return payStrategy.get(payKey);
    }
}
