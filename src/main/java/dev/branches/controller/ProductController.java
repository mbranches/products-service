package dev.branches.controller;

import dev.branches.dto.response.ProductGetResponse;
import dev.branches.dto.request.ProductPostRequest;
import dev.branches.dto.response.ProductPostResponse;
import dev.branches.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ProductPostResponse> create(@Valid @RequestBody ProductPostRequest postRequest) {
        ProductPostResponse response = service.create(postRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
