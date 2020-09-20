package com.cgb.luofenwu.pattern.factory.simplefactory;

import com.cgb.luofenwu.pattern.factory.IParse;
import com.cgb.luofenwu.pattern.factory.XmlParse;

import java.util.HashMap;
import java.util.Map;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/1318:44
 * @since
 */
public class SimpleParseFactoryWithMap {
    public static final Map<String, IParse> map = new HashMap<String, IParse>(2);

    static {
        map.put("xml", new XmlParse());
        map.put("json", new XmlParse());
    }

    public static IParse create(String format) {
        if (null == format || "".equals(format)) {
            return null;
        }
        IParse parse = map.get(format);
        return parse;
    }
}
