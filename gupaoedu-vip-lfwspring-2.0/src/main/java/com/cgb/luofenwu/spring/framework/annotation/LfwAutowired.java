package com.cgb.luofenwu.spring.framework.annotation;

import java.lang.annotation.*;


/**
 * 自动注入
 * @Author LFW
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LfwAutowired {
	String value() default "";
}
