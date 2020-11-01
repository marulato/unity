package org.legion.unity.common.validation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchesPattern {

    String pattern();

    String message();

    String[] profile() default {};

}
