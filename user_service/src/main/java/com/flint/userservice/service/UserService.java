package com.flint.userservice.service;

import com.flint.userservice.dto.RegisterRequestDto;
import com.flint.userservice.dto.UserResponseDto;

public interface UserService {
    UserResponseDto register(RegisterRequestDto request);
}
