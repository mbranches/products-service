package dev.branches.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record SaleProductBySalePostResponse(
        @Schema(description = "product name", example = "Monitor UltraWide LG")
        String name,
        @Schema(description = "product unit price", example = "1800.00")
        Double unitPrice,
        @Schema(description = "product quantity", example = "10")
        Integer quantity,
        @Schema(description = "total value", example = "18000.00")
        Double totalValue
) {}
