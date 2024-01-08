package com.vtu.api.controller;

import com.vtu.api.dto.request.UserRegistrationRequest;
import com.vtu.api.dto.response.ApiResponse;
import com.vtu.api.dto.response.RegistrationResponse;
import com.vtu.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(description = "Register as a new user")
    public ResponseEntity<ApiResponse<RegistrationResponse>> register(@RequestBody @Valid
                                                                      UserRegistrationRequest request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }

}
