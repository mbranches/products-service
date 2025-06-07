package dev.branches.dto.response;

import java.util.List;

public record SalePostResponse(Long id, UserBySalePostResponse customer, List<SaleProductBySalePostResponse> products, Double totalValue) {}
