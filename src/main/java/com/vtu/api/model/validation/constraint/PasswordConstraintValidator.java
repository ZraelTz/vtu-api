package com.vtu.api.model.validation.constraint;


import com.vtu.api.model.validation.PasswordChecker;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;

import static com.vtu.api.model.constants.RegexConstant.PASSWORD_REGEX_PATTERN;

public class PasswordConstraintValidator implements ConstraintValidator<PasswordChecker, String> {

    @Override
    public void initialize(PasswordChecker constraintAnnotation) {
    }

    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(fieldValue)) {
            return false;
        }

        Matcher matcher = PASSWORD_REGEX_PATTERN.matcher(fieldValue);
        return matcher.find();
    }
}
