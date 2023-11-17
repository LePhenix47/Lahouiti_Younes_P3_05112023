package com.openclassrooms.p3.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private static final String[] AUTHENTICATION_NEEDED_ROUTES = {
            "/api/messages/**",
            "/api/rentals/**",
            "/api/users/**",
            "/api/auth/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers(
                         "/api/messages/**",
            "/api/rentals/**",
            "/api/users/**",
            "/api/auth/**").permitAll().anyRequest().authenticated());

        return http.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}