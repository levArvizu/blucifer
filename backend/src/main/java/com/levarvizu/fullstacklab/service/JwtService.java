package com.levarvizu.fullstacklab.service;

import java.util.List;

import com.levarvizu.fullstacklab.entity.User;

public interface JwtService {

    String generateToken(User user);

    String extractUsername(String token);

    boolean isValid(String token);

    public String refreshToken(String oldToken);

    public void invalidateToken(String token, String reason);

    List<String> extractRoles(String token);

    public boolean isInvalidated(String token);

}
