package com.levarvizu.fullstacklab.service;

import com.levarvizu.fullstacklab.dto.AuthResponse;
import com.levarvizu.fullstacklab.dto.LoginRequest;
import com.levarvizu.fullstacklab.dto.RegisterRequest;

public interface AuthService {

        AuthResponse register(RegisterRequest request);

        AuthResponse login(
                        LoginRequest request);

        void logout(String token);

        AuthResponse refreshToken(String oldToken);

}
