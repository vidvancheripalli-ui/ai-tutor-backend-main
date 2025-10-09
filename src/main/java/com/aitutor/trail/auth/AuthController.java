package com.aitutor.trail.auth;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        authService.register(request.getUsername(), request.getPassword());

        AuthResponse response = new AuthResponse("success", "Registration successful", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpSession session) {
        User user = authService.login(request.getUsername(), request.getPassword());
        session.setAttribute("user", user);

        AuthResponse response = new AuthResponse("success", "Login successful", user.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(HttpSession session) {
        session.invalidate();

        AuthResponse response = new AuthResponse("success", "Logout successful", null);
        return ResponseEntity.ok(response);
    }
}
