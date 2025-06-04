package dev.branches.dto;

import dev.branches.model.Sale;

public record SalePostResponse(Long id, ProductBySalePostResponse product, Integer quantity, Double totalValue) {

    public static SalePostResponse of(Sale sale) {
        ProductBySalePostResponse product = ProductBySalePostResponse.of(sale.getProduct());

        return new SalePostResponse(sale.getId(), product, sale.getQuantity(), sale.getTotalValue());
    }
}
