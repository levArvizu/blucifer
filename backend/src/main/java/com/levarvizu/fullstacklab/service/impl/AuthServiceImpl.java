package com.levarvizu.fullstacklab.service.impl;

import com.levarvizu.fullstacklab.dto.AuthResponse;
import com.levarvizu.fullstacklab.dto.LoginRequest;
import com.levarvizu.fullstacklab.dto.RegisterRequest;
import com.levarvizu.fullstacklab.service.AuthService;
import org.springframework.stereotype.Service;

import com.levarvizu.fullstacklab.entity.User;
import com.levarvizu.fullstacklab.repository.UserRepository;
import com.levarvizu.fullstacklab.service.JwtService;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthServiceImpl implements AuthService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;

        public AuthServiceImpl(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        JwtService jwtService) {
                this.userRepository = userRepository;
                this.passwordEncoder = passwordEncoder;
                this.jwtService = jwtService;
        }

        @Override
        public AuthResponse register(RegisterRequest request) {
                if (userRepository.findByEmail(request.email()).isPresent()) {
                        throw new RuntimeException("Email already registered");
                }

                User user = new User();
                user.setEmail(request.email());
                user.setPassword(passwordEncoder.encode(request.password()));
                user.setRoles(List.of("USER")); // lista de roles por defecto

                userRepository.save(user);

                String token = jwtService.generateToken(user);
                return new AuthResponse(token, user.getEmail(), user.getRoles());
        }

        @Override
        public AuthResponse login(LoginRequest request) {
                User user = userRepository.findByEmail(request.email())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                if (!passwordEncoder.matches(request.password(), user.getPassword())) {
                        throw new RuntimeException("Invalid credentials");
                }

                String token = jwtService.generateToken(user);
                return new AuthResponse(token, user.getEmail(), user.getRoles());
        }

        @Override
        public void logout(String token) {
                jwtService.invalidateToken(token, "logout"); // si implementas blacklist en Redis
        }

        @Override
        public AuthResponse refreshToken(String oldToken) {
                if (!jwtService.isValid(oldToken)) {
                        throw new RuntimeException("Invalid token");
                }

                String newToken = jwtService.refreshToken(oldToken);

                // Extraer datos del usuario desde el token original
                String email = jwtService.extractUsername(oldToken);
                List<String> roles = jwtService.extractRoles(oldToken);

                return new AuthResponse(newToken, email, roles);
        }

}
