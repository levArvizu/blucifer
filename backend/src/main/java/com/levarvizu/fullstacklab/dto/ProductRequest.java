package com.levarvizu.fullstacklab.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "{product.name.required}")
        String name,

        @NotBlank(message = "{product.description.required}")
        String description,

        @Positive(message = "{product.price.positive}")
        BigDecimal price
) {
}