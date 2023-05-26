package com.final_project.staselko.utils.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidator implements ConstraintValidator<DateValid, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value.length() != 0){
            LocalDate desiredDate = LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate date = LocalDate.now();
            if (date.isBefore(desiredDate) || date.isEqual(desiredDate)) {
                return true;
            }
        }else {
            return true;
        }
        return false;
    }
}
