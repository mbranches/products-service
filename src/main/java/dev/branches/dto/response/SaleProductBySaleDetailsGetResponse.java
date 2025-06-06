package dev.branches.dto.response;

import dev.branches.model.Product;
import dev.branches.model.SaleProduct;

public record SaleProductBySaleDetailsGetResponse(Long id, String name, Double unitPrice, Integer quantity, Double totalValue) {
    public static SaleProductBySaleDetailsGetResponse of(SaleProduct saleProduct) {
        Product product = saleProduct.getProduct();

        return new SaleProductBySaleDetailsGetResponse(saleProduct.getId(), product.getName(), product.getUnitPrice(), saleProduct.getQuantity(), saleProduct.getTotalValue());
    }
}
