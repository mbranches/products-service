package dev.branches.dto.response;

public record SaleProductBySaleDetailsGetResponse(Long id, String name, Double unitPrice, Integer quantity, Double totalValue) {}
