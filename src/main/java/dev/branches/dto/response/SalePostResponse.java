package dev.branches.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SalePostResponse(
        @Schema(description = "sale id saved in database", example = "1")
        Long id,
        @Schema(description = "requesting user")
        UserBySalePostResponse customer,
        @Schema(description = "sale products")
        List<SaleProductBySalePostResponse> products,
        @Schema(description = "sale total value", example = "18000.00")
        Double totalValue
) {}
