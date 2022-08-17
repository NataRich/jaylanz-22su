package com.jaylanz.common.validation.constraint;

import com.jaylanz.common.validation.validator.EmailAddressValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {EmailAddressValidator.class}
)
public @interface EmailAddress {
    public String message() default "jaylanz.common.validation.constraint.EmailAddress";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
