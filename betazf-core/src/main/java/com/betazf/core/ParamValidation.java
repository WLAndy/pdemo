package com.betazf.core;


import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamValidation {

    boolean validation() default false;

    String paramMessage() default "参数不可为空";
}
