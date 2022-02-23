package icu.helltab.itool.verify.annotation;

import icu.helltab.itool.verify.core.BaseVerifyRule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证接口入参
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ParamVerify {
    Class<? extends BaseVerifyRule<?>>[] rules() default {};

    /**
     * 参数是否必须, 默认为必须
     * @return
     */
    boolean must() default true;

    /**
     * 联合非空, 相同的 key, 只要有一个不为空即可
     * @return
     */
    String mustKey() default "";
}
