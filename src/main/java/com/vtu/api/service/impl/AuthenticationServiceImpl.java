package com.vtu.api.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtu.api.core.exception.ApiException;
import com.vtu.api.core.exception.NotFoundException;
import com.vtu.api.dto.request.LoginRequest;
import com.vtu.api.dto.response.ApiResponse;
import com.vtu.api.dto.response.LoginResponse;
import com.vtu.api.model.entity.User;
import com.vtu.api.repository.UserRepository;
import com.vtu.api.service.AuthenticationService;
import com.vtu.api.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        if (Objects.nonNull(authentication) && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            LoginResponse loginResponse = LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .email(user.getEmail())
                    .build();

            return ApiResponse.<LoginResponse>builder()
                    .data(loginResponse)
                    .message("Login successful")
                    .build();
        }

        throw new ApiException("Login failed");
    }


    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String email;
        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        email = jwtService.extractUsername(refreshToken);
        if (StringUtils.isNotBlank(authHeader)) {
            User user = this.userRepository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException("Email not found"));

            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);

                LoginResponse loginResponse = LoginResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .email(user.getEmail())
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), loginResponse);
            }
        }
    }

    @Override
    public User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            String email;
            if (principal instanceof User) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                email = userDetails.getUsername();
            } else {
                email = authentication.getName();
            }

            return userRepository.findByEmail(email).orElse(null);
        }

        return null;
    }


}
