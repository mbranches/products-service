package dev.branches.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductPostRequest(
        @Schema(description = "product name", example = "Monitor UltraWide LG")
        @NotBlank(message = "The field 'name' is required")
        String name,
        @Schema(description = "product unit price", example = "1800.00")
        @NotNull(message = "The field 'unitPrice' is required")
        Double unitPrice
) {}
