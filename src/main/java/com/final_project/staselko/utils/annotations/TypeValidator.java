package com.final_project.staselko.utils.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TypeValidator implements ConstraintValidator<FileTypeValid, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        String type = value.substring(value.lastIndexOf(".") + 1);
        return type.equals("pdf") || type.equals("png") || type.equals("doc") || type.equals("docx") || type.equals("jpg") || type.equals("jpeg");
    }
}
