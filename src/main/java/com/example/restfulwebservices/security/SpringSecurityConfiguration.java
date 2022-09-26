package com.example.restfulwebservices.security;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Overrides the security to customize how we want to define our security
 */
@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // All requests should be authenticated
        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated()
        );
        // If a request is not authenticated, a webpage is shown to input credentials
        http.httpBasic(Customizer.withDefaults());

        // Disable CSRF for POST, PUT
        http.csrf().disable();
        return http.build();
    }

}
