package com.vtu.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest {

    @JsonProperty("email")
    @NotBlank(message = "Email is blank")
    private String username;

    @NotBlank(message = "Password is blank")
    private String password;
}
