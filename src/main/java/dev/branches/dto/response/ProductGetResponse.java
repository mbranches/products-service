package dev.branches.dto.response;

import dev.branches.model.Product;

public record ProductGetResponse(Long id, String name, Double unitPrice) {
    public static ProductGetResponse of(Product product) {
        return new ProductGetResponse(product.getId(), product.getName(), product.getUnitPrice());
    }
}
