package dev.branches.dto.response;

import dev.branches.model.Product;

public record ProductBySalePostResponse(Long id, String name, Double unitPrice) {
    public static ProductBySalePostResponse of(Product product) {
        return new ProductBySalePostResponse(product.getId(), product.getName(), product.getUnitPrice());
    }
}
