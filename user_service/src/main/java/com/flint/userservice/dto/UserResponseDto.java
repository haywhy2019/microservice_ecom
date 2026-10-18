package com.flint.userservice.dto;

import com.flint.userservice.entity.User;
import com.flint.userservice.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole() != null ? user.getRole().name() : RoleName.CUSTOMER.toString()
        );
    }
}
