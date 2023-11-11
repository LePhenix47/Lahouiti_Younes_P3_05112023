package com.openclassrooms.p3.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @PostMapping("/")
    public ResponseEntity<?> postMessage(@RequestBody Map<String, Object> request) {
        // TODO: Implement postMessage logic
    }
}
