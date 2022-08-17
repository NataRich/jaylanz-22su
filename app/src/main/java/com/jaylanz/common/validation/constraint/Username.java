package com.jaylanz.common.validation.constraint;

import com.jaylanz.common.validation.validator.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {UsernameValidator.class}
)
public @interface Username {
    public String message() default "jaylanz.common.validation.constraint.NullableUsername";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
