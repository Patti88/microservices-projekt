package se.iths.authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.authserver.model.User;
import se.iths.authserver.service.AuthService;
import se.iths.authserver.service.JwtService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        if (authService.authenticate(user.username(), user.password())) {
            String token = jwtService.generateToken(user.username());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}