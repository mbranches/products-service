package dev.branches.dto;

import dev.branches.model.Product;

public record ProductBySaleGetResponse(String name, Double unitPrice) {
    public static ProductBySaleGetResponse of(Product product) {
        return new ProductBySaleGetResponse(product.getName(), product.getUnitPrice());
    }
}
