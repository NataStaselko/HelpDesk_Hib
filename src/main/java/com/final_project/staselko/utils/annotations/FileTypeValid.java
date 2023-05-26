package com.final_project.staselko.utils.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = TypeValidator.class)
@Documented
public @interface FileTypeValid {
    String message() default "The selected file type is not allowed. Please select a file of one of the following types: pdf, png, doc, docx, jpg, jpeg.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
