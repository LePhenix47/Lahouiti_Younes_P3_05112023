package com.openclassrooms.p3.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private static final String[] AUTH_NEEDED_ROUTES = {
            "/api/messages/**",
            "/api/rentals/**",
            "/api/users/**",
            "/api/auth/me"
    };

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // return http.authorizeHttpRequests((auth) -> {
    // for (String route : AUTH_NEEDED_ROUTES) {
    // auth.requestMatchers(route).authenticated();
    // }
    // })
    // .sessionManagement(session -> session
    // .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    // // .formLogin(Customizer.withDefaults())
    // .build();
    // }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}