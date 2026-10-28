package com.levarvizu.fullstacklab.dto;

public record RegisterRequest(
        String email,
        String password
) {
}