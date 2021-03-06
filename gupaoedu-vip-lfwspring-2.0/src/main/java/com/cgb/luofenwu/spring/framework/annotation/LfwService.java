package com.cgb.luofenwu.spring.framework.annotation;

import java.lang.annotation.*;

/**
 * @Author LFW
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LfwService {

	String value() default "";
}
