package jp.amamon.simple.client.annotation;

import java.lang.annotation.*;

/**
 * java will not compile the parameter's name by default,
 * so use this annotation to set param name.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE})
public @interface ParamName {
    String value() default "";
}
