package com.vtu.api.model.validation;

import com.vtu.api.model.validation.constraint.EnumCheckerValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnumCheckerValidator.class)
public @interface NotEnum {

    Class<? extends Enum<?>> enumClass();

    String message() default "Invalid enum type passed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
