package dev.branches.dto.response;

public record SaleGetResponse(Long id, UserBySaleGetResponse customer, Double totalValue) {}
