package dev.branches.dto.response;

import dev.branches.model.Product;

import java.util.List;

public record ProductGetResponse(Long id, String name, Double unitPrice) {
    public static ProductGetResponse of(Product product) {
        return new ProductGetResponse(product.getId(), product.getName(), product.getUnitPrice());
    }

    public static List<ProductGetResponse> productGetResponseListOf(List<Product> productList) {
        return productList.stream().map(ProductGetResponse::of).toList();
    }
}
