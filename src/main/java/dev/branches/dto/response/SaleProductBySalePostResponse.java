package dev.branches.dto.response;

import dev.branches.model.Product;
import dev.branches.model.SaleProduct;

public record SaleProductBySalePostResponse(String name, Double unitPrice, Integer quantity, Double totalValue) {
    public static SaleProductBySalePostResponse of(SaleProduct saleProduct) {
        Product product = saleProduct.getProduct();

        return new SaleProductBySalePostResponse(product.getName(), product.getUnitPrice(), saleProduct.getQuantity(), saleProduct.getTotalValue());
    }
}
