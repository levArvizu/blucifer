package com.levarvizu.fullstacklab.dto;

import java.util.Map;

public record ValidationErrorResponse(
        Map<String, String> errors
) {
}