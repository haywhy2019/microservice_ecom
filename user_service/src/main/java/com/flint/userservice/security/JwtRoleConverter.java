package com.flint.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
public class JwtRoleConverter {

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        // Read roles from our custom JWT claim.
        authoritiesConverter.setAuthoritiesClaimName("roles");

        // Convert CUSTOMER into ROLE_CUSTOMER.
        authoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter authenticationConverter =
                new JwtAuthenticationConverter();

        authenticationConverter.setJwtGrantedAuthoritiesConverter(
                authoritiesConverter
        );

        return authenticationConverter;
    }
}
