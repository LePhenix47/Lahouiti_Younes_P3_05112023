package com.openclassrooms.p3.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/")
    public ResponseEntity<?> getUser(@RequestParam Long id) {
        // TODO: Implement getUser logic
    }
}
