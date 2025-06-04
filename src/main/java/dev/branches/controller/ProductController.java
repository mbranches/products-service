package dev.branches.controller;

import dev.branches.dto.ProductGetResponse;
import dev.branches.dto.ProductPostRequest;
import dev.branches.dto.ProductPostResponse;
import dev.branches.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;
    @GetMapping
    public ResponseEntity<List<ProductGetResponse>> findAll() {
        List<ProductGetResponse> response = service.findAll();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductPostResponse> create(ProductPostRequest postRequest) {
        ProductPostResponse response = service.create(postRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
