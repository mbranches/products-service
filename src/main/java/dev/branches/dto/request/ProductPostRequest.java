package dev.branches.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductPostRequest(
        @NotBlank(message = "The field 'name' is required")
        String name,
        @NotNull(message = "The field 'unitPrice' is required")
        Double unitPrice
) {}
