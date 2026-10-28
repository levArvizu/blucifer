package com.levarvizu.fullstacklab.dto;

import java.util.List;

public record UserDetailsResponse(Long id, String email, List<String> actions) {
}
