package com.cgb.luofenwu.spring.framework.annotation;

import java.lang.annotation.*;

/**
 * 业务逻辑,注入接口
 * @Author LFW
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LfwService {
	String value() default "";
}
