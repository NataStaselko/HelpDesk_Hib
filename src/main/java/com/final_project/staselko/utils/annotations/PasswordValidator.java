package com.final_project.staselko.utils.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value.matches(".*[A-Z].*")
                && value.matches(".*[a-z].*")
                && value.matches(".*[0-9].*")
                && value.matches(".*[~.\"(),:;\\[\\]<>@!#$%&'*+-/=?^_`{|}].*")
                && value.matches("^.{6,20}$")) {
            return true;
        }
        return false;
    }
}
