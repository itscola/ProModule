package top.whitecola.promodule.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface ModuleSetting {
    String name() default "setting";
    String type() default "value";
    float min() default 0;
    float max() default 200;
}
