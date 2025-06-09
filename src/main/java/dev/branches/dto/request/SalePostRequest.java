package dev.branches.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SalePostRequest(
        @Schema(description = "sale products")
        @NotNull(message = "The field 'products' is required")
        List<SaleProductBySalePostRequest> products
) {}
