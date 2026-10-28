package com.levarvizu.fullstacklab.controller;

import com.levarvizu.fullstacklab.dto.AuthResponse;
import com.levarvizu.fullstacklab.dto.LoginRequest;
import com.levarvizu.fullstacklab.dto.RegisterRequest;
import com.levarvizu.fullstacklab.service.AuthService;
import com.levarvizu.fullstacklab.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;
    private final JwtService jwtService;

    public AuthController(
            AuthService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {

        service.register(request);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestHeader("Authorization") String authHeader) {
        String oldToken = authHeader.substring(7); // quitar "Bearer "
        return ResponseEntity.ok(service.refreshToken(oldToken));
    }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("Solo los administradores pueden acceder a este dashboard");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // Invalida el token en Redis con motivo "logout"
            jwtService.invalidateToken(token, "logout");

            return ResponseEntity.ok("Token invalidado correctamente");
        }

        return ResponseEntity.badRequest().body("No se encontró token en el header");
    }

}
