package com.flint.userservice.service;

import com.flint.userservice.dto.RegisterRequestDto;
import com.flint.userservice.dto.UserResponseDto;
import com.flint.userservice.entity.User;
import com.flint.userservice.enums.RoleName;
import com.flint.userservice.repository.UserRepository;
import com.flint.userservice.service.imp.UserServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImp userService;

    @Test
    void register_shouldHashPasswordBeforeSavinUser() {
        RegisterRequestDto request = new RegisterRequestDto();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");
        request.setPassword("password");

        String rawPassword = "password";
        String hashedPassword = "$2a$12$exampleHashForTesting";

        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(passwordEncoder.encode(rawPassword)).thenReturn(hashedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponseDto response = userService.register(request);

        verify(passwordEncoder).encode(rawPassword);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertEquals(hashedPassword, savedUser.getPassword());
        assertNotEquals(rawPassword, savedUser.getPassword());
        assertEquals(RoleName.CUSTOMER, savedUser.getRole());
        assertEquals("john@example.com", response.getEmail());
        verifyNoMoreInteractions(userRepository);
    }
}
