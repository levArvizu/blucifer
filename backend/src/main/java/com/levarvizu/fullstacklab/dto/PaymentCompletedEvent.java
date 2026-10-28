package com.levarvizu.fullstacklab.dto;

import java.math.BigDecimal;

public record PaymentCompletedEvent(
        Long orderId,
        BigDecimal amount
) {
}