package com.flint.userservice.service;

import com.flint.userservice.dto.LoginRequestDto;
import com.flint.userservice.dto.LoginResponseDto;
import com.flint.userservice.dto.RegisterRequestDto;
import com.flint.userservice.dto.UserResponseDto;

public interface UserService {
    UserResponseDto register(RegisterRequestDto request);

    LoginResponseDto login(LoginRequestDto request);
}
