package dev.branches.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SaleBySaleDetailsGetResponse(
        @Schema(description = "sale id saved in database", example = "1")
        Long id,
        @Schema(description = "customer owner of the sale")
        UserBySaleGetResponse customer,
        @Schema(description = "all products of the sale")
        List<SaleProductBySaleDetailsGetResponse> products,
        @Schema(description = "sale total value", example = "18000.00")
        Double totalValue
) {}
