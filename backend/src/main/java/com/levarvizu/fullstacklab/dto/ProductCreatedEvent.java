package com.levarvizu.fullstacklab.dto;

import java.math.BigDecimal;

public record ProductCreatedEvent(
        Long id,
        String name,
        BigDecimal price
) {
}