package com.vtu.api.model.validation;

import com.vtu.api.model.validation.constraint.PasswordConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.vtu.api.model.constants.RegexConstant.INVALID_PASSWORD_ERR_MSG;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Documented
public @interface PasswordChecker {

    String message() default INVALID_PASSWORD_ERR_MSG;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
