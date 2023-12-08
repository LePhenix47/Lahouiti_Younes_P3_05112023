package com.openclassrooms.p3.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This class is a configuration class that sets up the security configuration
 * for a Spring Boot application.
 * It enables web security and defines the necessary security filters and rules.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private static final String[] AUTHENTICATION_NEEDED_ROUTES = {
            "/api/messages/**",
            "/api/rentals/**",
            "/api/user/**",
            "/api/auth/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/swagger-config",
            "/v3/api-docs"
    };
    public static final String passwordEncoder = null;

    /**
     * Configures the security filter chain by disabling CSRF protection, setting
     * the session creation policy to stateless,
     * and defining authorization rules for specific routes.
     * 
     * @param http the HttpSecurity object to configure the security filter chain
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers(
                        AUTHENTICATION_NEEDED_ROUTES).permitAll().anyRequest().authenticated());

        return http.build();
    }

    /**
     * Creates a BCryptPasswordEncoder bean.
     * 
     * @return the BCryptPasswordEncoder bean
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}