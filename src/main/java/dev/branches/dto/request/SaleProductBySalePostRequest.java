package dev.branches.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.With;

@With
public record SaleProductBySalePostRequest(
        @Schema(description = "product id", example = "1")
        @NotNull(message = "The field 'productId' is required")
        Long productId,
        @Schema(description = "product quantity", example = "10")
        @NotNull(message = "The field 'quantity' is required")
        Integer quantity
) {}
