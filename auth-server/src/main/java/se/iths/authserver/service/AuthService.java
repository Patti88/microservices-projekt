package se.iths.authserver.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    // Simulerad användardatabas (bör ersättas med en riktig databas i produktion)
    private final Map<String, String> users = new HashMap<>();

    public AuthService() {
        users.put("user", "password");
        users.put("admin", "admin");
    }

    public boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}