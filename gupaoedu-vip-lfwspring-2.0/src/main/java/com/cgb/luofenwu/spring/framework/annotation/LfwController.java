package com.cgb.luofenwu.spring.framework.annotation;

import java.lang.annotation.*;

/**
 * 页面交互
 * @Author LFW
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LfwController {
	String value() default "";
}
