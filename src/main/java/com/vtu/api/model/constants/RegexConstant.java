package com.vtu.api.model.constants;

import java.util.regex.Pattern;

public class RegexConstant {

    public static final String NIGERIAN_PHONE_NO_REGEX = "^(0|\\+234)\\d{10}$";
    public static final String EMAIL_REGEX = ".+[@].+[\\.].+";

    public static final Pattern EMAIL_REGEX_PATTERN = Pattern.compile(EMAIL_REGEX);
    public static final Pattern PASSWORD_REGEX_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\\[\\]$\\-_%^&~+|=<>?/\'\"^.,:;*!(){}])(?=\\S+$).{8,20}$");

    public static final String INVALID_PASSWORD_ERR_MSG = "Password must have at least one uppercase letter, " +
            "one lowercase letter, one special character and one number. " +
            "It must also be a minimum of 8 and maximum of 20 characters";
}
