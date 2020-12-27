package com.cgb.luofenwu.spring.framework.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LfwRequestMapping {
    String value() default "";
}
