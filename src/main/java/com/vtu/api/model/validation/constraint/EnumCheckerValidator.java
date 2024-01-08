package com.vtu.api.model.validation.constraint;

import com.vtu.api.model.validation.NotEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumCheckerValidator implements ConstraintValidator<NotEnum, CharSequence> {

    private List<String> acceptedValues;

    @Override
    public void initialize(NotEnum constraintAnnotation) {
        acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        if (charSequence == null) {
            return true;
        }
        return acceptedValues.contains(charSequence.toString());
    }
}
