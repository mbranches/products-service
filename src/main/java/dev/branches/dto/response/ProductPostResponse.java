package dev.branches.dto.response;

import dev.branches.model.Product;

public record ProductPostResponse(Long id, String name, Double unitPrice) {
    public static ProductPostResponse of(Product product) {
        return new ProductPostResponse(product.getId(), product.getName(), product.getUnitPrice());
    }
}
