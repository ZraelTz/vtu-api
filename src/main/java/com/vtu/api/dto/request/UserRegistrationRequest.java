package com.vtu.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vtu.api.model.validation.FieldsValueMatch;
import com.vtu.api.model.validation.PasswordChecker;
import com.vtu.api.model.validation.order.FirstOrder;
import com.vtu.api.model.validation.order.FourthOrder;
import com.vtu.api.model.validation.order.SecondOrder;
import com.vtu.api.model.validation.order.ThirdOrder;
import com.vtu.api.model.constants.RegexConstant;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldsValueMatch.List({
        @FieldsValueMatch(field = "password", fieldMatch = "confirmPassword",
                message = "Password and Confirm Password do not match",
                groups = FourthOrder.class)})
@GroupSequence({UserRegistrationRequest.class, FirstOrder.class,
        SecondOrder.class, ThirdOrder.class, FourthOrder.class})
public class UserRegistrationRequest {

    @NotBlank(message = "Email is blank", groups = FirstOrder.class)
    @Email(message = "Email is invalid", regexp = RegexConstant.EMAIL_REGEX, groups = SecondOrder.class)
    private String email;

    @NotBlank(message = "Password is blank", groups = FirstOrder.class)
    @PasswordChecker(groups = SecondOrder.class)
    private String password;

    @NotBlank(message = "Confirm Password is blank")
    private String confirmPassword;
}
