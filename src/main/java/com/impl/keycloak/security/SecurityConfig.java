package com.impl.keycloak.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private static final String CLIENT_ID = "springboot_client";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth -> oauth
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {

            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");

            if (resourceAccess == null || !resourceAccess.containsKey(CLIENT_ID)) {
                return Collections.emptyList();
            }

            Map<String, Object> client =
                    (Map<String, Object>) resourceAccess.get(CLIENT_ID);

            Collection<String> roles =
                    (Collection<String>) client.get("roles");

            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        });

        return converter;
    }
}