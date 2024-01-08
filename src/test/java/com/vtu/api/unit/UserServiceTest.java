package com.vtu.api.unit;

import com.vtu.api.core.exception.ApiException;
import com.vtu.api.dto.request.UserRegistrationRequest;
import com.vtu.api.dto.response.ApiResponse;
import com.vtu.api.dto.response.RegistrationResponse;
import com.vtu.api.mapper.UserMapper;
import com.vtu.api.model.entity.User;
import com.vtu.api.repository.UserRepository;
import com.vtu.api.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private UserServiceImpl userService;


    private UserRegistrationRequest registrationRequest() {
        UserRegistrationRequest registrationRequest = new UserRegistrationRequest();
        registrationRequest.setEmail("test@test.com");
        registrationRequest.setPassword("$Test123");
        registrationRequest.setConfirmPassword("$Test123");

        return registrationRequest;
    }

    private User testUser() {
        User testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@test.com");
        testUser.setPassword("$Test123");

        return testUser;
    }
    @Test
    public void registerUser_success() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userMapper.createUser(any())).thenReturn(testUser());
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any())).thenReturn(testUser());

        ApiResponse<RegistrationResponse> response = userService.registerUser(registrationRequest());

        assertNotNull(response);
        assertEquals("Registration successful", response.getMessage());
        assertNotNull(response.getData());
        assertEquals(testUser().getEmail(), response.getData().getEmail());

        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userMapper, times(1)).createUser(any());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void registerUser_emailExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        assertThrows(ApiException.class, () -> userService.registerUser(registrationRequest()));

        verify(userRepository, times(1)).existsByEmail(anyString());
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userMapper);
        verifyNoInteractions(passwordEncoder);
    }
}
