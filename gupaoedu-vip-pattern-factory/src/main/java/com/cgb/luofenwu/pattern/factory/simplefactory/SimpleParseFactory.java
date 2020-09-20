package com.cgb.luofenwu.pattern.factory.simplefactory;

import com.cgb.luofenwu.pattern.factory.IParse;
import com.cgb.luofenwu.pattern.factory.JsonParse;
import com.cgb.luofenwu.pattern.factory.XmlParse;
import com.gupaoedu.vip.pattern.factory.ICourse;

/**
 * @Autor:LFW
 * @Description:解析器简单工厂
 * @Date:create in 2020/9/1318:36
 */
public class SimpleParseFactory {
    /**
     *
     * @param format
     * @return
     */
    public IParse createParse(String format) {
        if ("json".equals(format)) {
            return new JsonParse();
        } else if ("xml".equals(format)) {
            return new XmlParse();
        } else {
            throw new RuntimeException("暂不支持" + format + "解析");
        }
    }

    public IParse createParse(Class<? extends IParse> clazz) {
        try {
            if (null != clazz) {
                return clazz.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
