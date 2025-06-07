package dev.branches.dto.response;

import java.util.List;

public record SaleBySaleDetailsGetResponse(Long id, UserBySaleGetResponse customer, List<SaleProductBySaleDetailsGetResponse> products, Double totalValue) {}
