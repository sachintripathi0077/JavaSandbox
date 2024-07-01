package com.dev.accounts.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LongValidatorForAccountNumber.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidLong {
    String message() default "{jakarta.validation.constraints.ValidLong.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
