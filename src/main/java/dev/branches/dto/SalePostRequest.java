package dev.branches.dto;

import java.util.List;

public record SalePostRequest(List<SaleProductBySalePostRequest> products) {
}
