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
@Constraint(validatedBy = TextValidator.class)
@Documented
public @interface TextValid {
    String message() default "Invalid characters. You can use upper and lowercase English alpha characters, digits and special characters: ~.\"(),:;<>@[]!#$%&'*+-/=?^_`{|}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
