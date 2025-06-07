package dev.branches.utils;

import dev.branches.model.Product;
import dev.branches.model.Sale;
import dev.branches.model.SaleProduct;

public class SaleProductUtils {
    public static SaleProduct newSaleProductExisting() {
        Sale sale = SaleUtils.newSaleList().getFirst();
        Product product = ProductUtils.newProductList().getFirst();
        int quantity = 10;

        return SaleProduct.builder()
                .id(1L)
                .sale(sale)
                .product(product)
                .quantity(quantity)
                .totalValue(product.getUnitPrice() * quantity)
                .build();
    }

    public static SaleProduct newSaleProductToSave() {
        Sale sale = SaleUtils.newSaleList().getLast();
        Product product = ProductUtils.newProductList().getLast();

        int quantity = 10;

        return SaleProduct.builder()
                .sale(sale)
                .product(product)
                .quantity(quantity)
                .totalValue(product.getUnitPrice() * quantity)
                .build();
    }

    public static SaleProduct newSaleProductSaved() {
        return newSaleProductToSave().withId(2L);
    }
}
