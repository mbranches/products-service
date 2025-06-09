package dev.branches.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProductGetResponse(
        @Schema(description = "product id saved in database", example = "1")
        Long id,
        @Schema(description = "product name", example = "Monitor UltraWide LG")
        String name,
        @Schema(description = "product unit price", example = "1800.00")
        Double unitPrice
) {}
