package com.flint.userservice.controller;


import com.flint.userservice.dto.LoginRequestDto;
import com.flint.userservice.dto.LoginResponseDto;
import com.flint.userservice.dto.RegisterRequestDto;
import com.flint.userservice.dto.UserResponseDto;
import com.flint.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService authService;

    public AuthController(UserService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        UserResponseDto response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
