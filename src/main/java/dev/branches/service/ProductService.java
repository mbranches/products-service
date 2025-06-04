package dev.branches.service;

import dev.branches.dto.ProductGetResponse;
import dev.branches.dto.ProductPostRequest;
import dev.branches.dto.ProductPostResponse;
import dev.branches.model.Product;
import dev.branches.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public List<ProductGetResponse> findAll() {
        List<Product> products = repository.findAll();

        return products.stream()
                .map(ProductGetResponse::of)
                .toList();
    }

    public Product findByIdOrThrowsNotFoundException(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id \"%s\" not found"));
    }

    public ProductPostResponse create(ProductPostRequest postRequest) {
        Product productToSave = Product.of(postRequest);

        Product savedProduct = repository.save(productToSave);

        return ProductPostResponse.of(savedProduct);
    }
}
