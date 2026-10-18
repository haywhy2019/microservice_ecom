package com.flint.userservice.service.imp;

import com.flint.userservice.dto.RegisterRequestDto;
import com.flint.userservice.dto.UserResponseDto;
import com.flint.userservice.entity.User;
import com.flint.userservice.enums.RoleName;
import com.flint.userservice.repository.UserRepository;
import com.flint.userservice.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponseDto register(RegisterRequestDto request) {
        String email = request.getEmail().trim().toLowerCase(Locale.ROOT);
        if(userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already in use");
        }

        User user = new User();
        user.setFirstName(request.getFirstName().trim());
        user.setLastName(request.getLastName().trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(RoleName.CUSTOMER);

        User savedUser = userRepository.save(user);
        return UserResponseDto.from(savedUser);

    }
}
