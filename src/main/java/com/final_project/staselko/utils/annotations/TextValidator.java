package com.final_project.staselko.utils.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TextValidator implements ConstraintValidator<TextValid, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.matches("[a-zA-Z\\s\\d~.\"(),:;<>@\\[\\]!#$%&'*+-/=?^_`{|}]*")){
            return true;
        }
        return false;
    }
}
