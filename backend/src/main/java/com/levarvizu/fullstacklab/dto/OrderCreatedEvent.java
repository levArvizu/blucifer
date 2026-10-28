package com.levarvizu.fullstacklab.dto;

import java.math.BigDecimal;

public record OrderCreatedEvent(
        Long orderId,
        Long productId,
        Integer quantity,
        BigDecimal total
) {
}