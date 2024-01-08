package com.vtu.api.service;

import com.vtu.api.dto.request.LoginRequest;
import com.vtu.api.dto.response.ApiResponse;
import com.vtu.api.dto.response.LoginResponse;
import com.vtu.api.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

public interface AuthenticationService {

    @Transactional(readOnly = true)
    ApiResponse<LoginResponse> login(LoginRequest request);

    @Transactional(readOnly = true)
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    @Transactional(readOnly = true)
    User getAuthUser();
}
