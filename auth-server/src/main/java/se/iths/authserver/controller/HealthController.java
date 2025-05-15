package se.iths.authserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Auth Server: HealthController.java
@RestController
@RequestMapping("/auth")
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("{\"status\":\"UP\"}");
    }
}