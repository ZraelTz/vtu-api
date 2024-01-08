package com.vtu.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vtu.api.model.entity.enums.NetworkProvider;
import com.vtu.api.model.validation.NotEnum;
import com.vtu.api.model.validation.order.FirstOrder;
import com.vtu.api.model.validation.order.SecondOrder;
import com.vtu.api.model.validation.order.ThirdOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static com.vtu.api.model.constants.ConstraintConstant.MAXIMUM_AIRTIME_TOPUP;
import static com.vtu.api.model.constants.ConstraintConstant.MINIMUM_AIRTIME_TOPUP;
import static com.vtu.api.model.constants.RegexConstant.NIGERIAN_PHONE_NO_REGEX;
import static com.vtu.api.model.constants.TestConstant.MTN_PHONE_NO;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirtimeTopupRequest {

    @NotBlank(message = "Network Provider is blank", groups = FirstOrder.class)
    @NotEnum(enumClass = NetworkProvider.class, message = "Invalid Network Provider", groups = SecondOrder.class)
    @Schema(description = "acceptable values are: MTN, AIRTEL, GLO, NINE_MOBILE", example = "MTN")
    private String networkProvider;

    @NotBlank(message = "Phone Number is blank", groups = FirstOrder.class)
    @Pattern(regexp = NIGERIAN_PHONE_NO_REGEX, message = "Phone Number is invalid", groups = SecondOrder.class)
    @Schema(example = MTN_PHONE_NO)
    private String phoneNumber;

    @NotNull(message = "Amount must be provided", groups = FirstOrder.class)
    @Min(value = MINIMUM_AIRTIME_TOPUP, message = "Amount cannot be less than " + MINIMUM_AIRTIME_TOPUP, groups = SecondOrder.class)
    @Max(value = MAXIMUM_AIRTIME_TOPUP, message = "Amount cannot be more than " + MAXIMUM_AIRTIME_TOPUP, groups = ThirdOrder.class)
    private Integer amount;
}
