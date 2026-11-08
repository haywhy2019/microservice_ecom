package com.flint.userservice.service.imp;

import com.flint.userservice.dto.LoginRequestDto;
import com.flint.userservice.dto.LoginResponseDto;
import com.flint.userservice.dto.RegisterRequestDto;
import com.flint.userservice.dto.UserResponseDto;
import com.flint.userservice.entity.User;
import com.flint.userservice.enums.RoleName;
import com.flint.userservice.exception.DuplicateEmailException;
import com.flint.userservice.exception.InvalidCredentialsException;
import com.flint.userservice.repository.UserRepository;
import com.flint.userservice.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Locale;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    @Value("${security.jwt.expiration}")
    private long jwtExpiration;

    public UserServiceImp(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,JwtEncoder jwtEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    @Transactional
    public UserResponseDto register(RegisterRequestDto request) {
        String email = request.getEmail().trim().toLowerCase(Locale.ROOT);
        if(userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(
                    "An account with this email already exists"
            );
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

    @Transactional
    public LoginResponseDto login(LoginRequestDto request) {
        String email = request.getEmail().trim().toLowerCase(Locale.ROOT);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        Instant now = Instant.now();
        Instant expiresAt = now.plusMillis(jwtExpiration);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("user-service")
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(user.getId().toString())
                .claim("roles", user.getRole().name())
                .build();

        // 4. Sign the JWT
        String token = jwtEncoder.encode(
                JwtEncoderParameters.from(
                        org.springframework.security.oauth2.jwt.JwsHeader
                                .with(MacAlgorithm.HS256)
                                .build(),
                        claims
                )
        ).getTokenValue();

        // 5. Return login response
        return new LoginResponseDto(token, "Bearer");
    }
}
