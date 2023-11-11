package com.openclassrooms.p3.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        // TODO: Implement registration logic
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        // TODO: Implement login logic
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        // TODO: Implement getMe logic
    }
}
