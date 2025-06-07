package dev.branches.utils;

import dev.branches.dto.request.ProductPostRequest;
import dev.branches.dto.response.ProductGetResponse;
import dev.branches.dto.response.ProductPostResponse;
import dev.branches.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductUtils {
    public static List<Product> newProductList() {
        Product product1 = Product.builder().id(1L).name("Iphone 12").unitPrice(3456.5D).build();
        Product product2 = Product.builder().id(2L).name("Camisa azul PP").unitPrice(67.4D).build();
        Product product3 = Product.builder().id(2L).name("Notebook Asus").unitPrice(6500D).build();

        return new ArrayList<>(List.of(product1, product2, product3));
    }

    public static List<ProductGetResponse> newProductGetResponseList() {
        ProductGetResponse product1 = new ProductGetResponse(1L, "Iphone 12", 3456.5D);
        ProductGetResponse product2 = new ProductGetResponse(2L, "Camisa azul PP", 67.4D);
        ProductGetResponse product3 = new ProductGetResponse(3L, "Notebook Asus", 6500D);

        return new ArrayList<>(List.of(product1, product2, product3));
    }

    public static Product newProductToSave() {
        return Product.builder()
                .name("Cadeira Ergonômica")
                .unitPrice(649D)
                .build();
    }

    public static Product newProductSaved() {
        return newProductToSave().withId(4L);
    }

    public static ProductPostRequest newProductPostRequest() {
        Product productToSave = newProductToSave();

        return new ProductPostRequest(productToSave.getName(), productToSave.getUnitPrice());
    }

    public static ProductPostResponse newProductPostResponse() {
        Product savedProduct = newProductSaved();

        return new ProductPostResponse(savedProduct.getId(), savedProduct.getName(), savedProduct.getUnitPrice());
    }
}
