package com.vtu.api.service;

import com.vtu.api.dto.request.UserRegistrationRequest;
import com.vtu.api.dto.response.ApiResponse;
import com.vtu.api.dto.response.RegistrationResponse;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    @Transactional
    ApiResponse<RegistrationResponse> registerUser(UserRegistrationRequest request);

}
