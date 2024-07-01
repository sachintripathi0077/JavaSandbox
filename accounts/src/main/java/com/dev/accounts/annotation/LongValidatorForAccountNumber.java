package com.dev.accounts.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LongValidatorForAccountNumber implements ConstraintValidator<ValidLong, Long> {
    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("In isValid : LongValidatorForAccount");
        if(aLong == null)
            return false;
        return aLong.toString().matches("(^$|[0-9]{10})");
    }
}
