package com.vtu.api.service.impl;

import com.vtu.api.core.exception.ApiException;
import com.vtu.api.dto.request.UserRegistrationRequest;
import com.vtu.api.dto.response.ApiResponse;
import com.vtu.api.dto.response.RegistrationResponse;
import com.vtu.api.mapper.UserMapper;
import com.vtu.api.model.entity.User;
import com.vtu.api.repository.UserRepository;
import com.vtu.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<RegistrationResponse> registerUser(UserRegistrationRequest request) {
        String email = request.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new ApiException("Email already exists");
        }

        User user = userMapper.createUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        RegistrationResponse registrationResponse = RegistrationResponse.builder()
                .email(email)
                .build();

        return ApiResponse.<RegistrationResponse>builder()
                .data(registrationResponse)
                .message("Registration successful")
                .build();
    }


}
