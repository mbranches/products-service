package dev.branches.service;

import dev.branches.dto.response.ProductGetResponse;
import dev.branches.dto.request.ProductPostRequest;
import dev.branches.dto.response.ProductPostResponse;
import dev.branches.exception.NotFoundException;
import dev.branches.model.Product;
import dev.branches.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public List<ProductGetResponse> findAll() {
        List<Product> products = repository.findAll();

        return ProductGetResponse.productGetResponseListOf(products);
    }

    public Product findByIdOrThrowsNotFoundException(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id '%s' not found".formatted(id)));
    }

    public ProductPostResponse create(ProductPostRequest postRequest) {
        Product productToSave = Product.of(postRequest);

        Product savedProduct = repository.save(productToSave);

        return ProductPostResponse.of(savedProduct);
    }
}
