package dev.branches.dto;

import dev.branches.model.Sale;

public record SaleGetResponse(Long id, ProductBySaleGetResponse product, Integer quantity, Double totalValue) {
    public static SaleGetResponse of(Sale sale) {
        ProductBySaleGetResponse product = ProductBySaleGetResponse.of(sale.getProduct());

        return new SaleGetResponse(sale.getId(), product, sale.getQuantity(), sale.getTotalValue());
    }
}
