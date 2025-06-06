package dev.branches.dto.request;

import jakarta.validation.constraints.NotNull;

public record SaleProductBySalePostRequest(
        @NotNull(message = "The field 'productId' is required")
        Long productId,
        @NotNull(message = "The field 'quantity' is required")
        Integer quantity
) {}
