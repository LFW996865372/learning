package com.cgb.luofenwu.pattern.factory.factorymetchod;

import com.cgb.luofenwu.pattern.factory.IParse;
import com.cgb.luofenwu.pattern.factory.JsonParse;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/9/1318:52
 */
public class JsonParseFactory implements IParseFactory {

    public IParse createParse() {
        return new JsonParse();
    }
}
