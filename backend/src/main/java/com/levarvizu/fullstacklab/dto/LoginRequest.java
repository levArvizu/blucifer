package com.levarvizu.fullstacklab.dto;

public record LoginRequest(
        String email,
        String password
) {
}