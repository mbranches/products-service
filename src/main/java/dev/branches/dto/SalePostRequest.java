package dev.branches.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SalePostRequest(
        @NotNull(message = "The field 'products' is required") @Valid
        List<SaleProductBySalePostRequest> products
) {}
