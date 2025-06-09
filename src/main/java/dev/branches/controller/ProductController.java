package dev.branches.controller;

import dev.branches.dto.response.ProductGetResponse;
import dev.branches.dto.request.ProductPostRequest;
import dev.branches.dto.response.ProductPostResponse;
import dev.branches.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Products", description = "Operations with products")
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @Operation(
            summary = "List all products",
            responses = {
                    @ApiResponse(
                            description = "all products listed successfully",
                            responseCode = "200",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(
                                            implementation = ProductGetResponse.class
                                    ))
                            )
                    ),
                    @ApiResponse(
                            description = "requesting user is not authenticated",
                            responseCode = "403",
                            content = @Content
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<ProductGetResponse>> findAll() {
        List<ProductGetResponse> response = service.findAll();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Create product",
            responses = {
                    @ApiResponse(
                            description = "product created successfully",
                            responseCode = "201",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ProductPostResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "the requesting user does not have the ADMIN role",
                            responseCode = "403",
                            content = @Content
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ProductPostResponse> create(@Valid @RequestBody ProductPostRequest postRequest) {
        ProductPostResponse response = service.create(postRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
