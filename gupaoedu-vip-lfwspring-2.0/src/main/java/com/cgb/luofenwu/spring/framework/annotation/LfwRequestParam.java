package com.cgb.luofenwu.spring.framework.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LfwRequestParam {

    String value() default "";
    boolean required() default true;
}
