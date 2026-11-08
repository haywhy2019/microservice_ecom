package com.flint.userservice.dto;

import com.flint.userservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class LoginResponseDto{
    String accessToken;
    String tokenType;

}

