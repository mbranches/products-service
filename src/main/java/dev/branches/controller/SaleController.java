package dev.branches.controller;

import dev.branches.dto.response.SaleBySaleDetailsGetResponse;
import dev.branches.dto.response.SaleGetResponse;
import dev.branches.dto.request.SalePostRequest;
import dev.branches.dto.response.SalePostResponse;
import dev.branches.exception.ArgumentNotValidMessageError;
import dev.branches.exception.DefaultMessageError;
import dev.branches.model.User;
import dev.branches.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Sales", description = "Operations with sales")
@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService service;

    @Operation(
            summary = "List all sales",
            responses = {
                    @ApiResponse(
                            description = "all sales listed successfully",
                            responseCode = "200",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(
                                            implementation = SaleGetResponse.class
                                    ))
                            )
                    ),
                    @ApiResponse(
                            description = "the requesting user does not have at least the MANAGER role",
                            responseCode = "403",
                            content = @Content
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<SaleGetResponse>> findAll() {
        List<SaleGetResponse> response = service.findAll();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "show sale details by sale id",
            parameters = @Parameter(name = "id", description = "sale id", example = "1"),
            responses = {
                    @ApiResponse(
                            description = "sale details displayed successfully",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SaleBySaleDetailsGetResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "the given sale id was not found",
                            responseCode = "404",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = DefaultMessageError.class,
                                            example = """
                                                    {
                                                        "id": 404,
                                                        "message": "Sale with id '999' not found"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "the requesting user does not have at least the MANAGER role",
                            responseCode = "403",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{id}/details")
    public ResponseEntity<SaleBySaleDetailsGetResponse> findSaleDetailsById(@PathVariable Long id) {
        SaleBySaleDetailsGetResponse response = service.findSaleDetailsById(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "list all sales of the requesting user",
            parameters = @Parameter(name = "id", description = "sale id", example = "1"),
            responses = {
                    @ApiResponse(
                            description = "sale details displayed successfully",
                            responseCode = "200",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = SaleGetResponse.class
                                            )
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "requesting user is not authenticated",
                            responseCode = "403",
                            content = @Content
                    )
            }
    )
    @GetMapping("/me")
    public ResponseEntity<List<SaleGetResponse>> findMySales(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        List<SaleGetResponse> response = service.findAllByUser(user);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "show sale details by sale id of the requesting user",
            parameters = @Parameter(name = "id", description = "sale id", example = "1"),
            responses = {
                    @ApiResponse(
                            description = "sale details displayed successfully",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SaleBySaleDetailsGetResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "the given sale id was not found",
                            responseCode = "404",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = DefaultMessageError.class,
                                            example = """
                                                    {
                                                        "id": 404,
                                                        "message": "Sale with id '999' not found"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "the sale given does not belongs to the requesting user or requesting user is not authenticated",
                            responseCode = "403",
                            content = @Content
                    )
            }
    )
    @GetMapping("/me/{id}/details")
    public ResponseEntity<SaleBySaleDetailsGetResponse> findMySaleDetailsById(Authentication authentication, @PathVariable Long id) {
        User user = (User) authentication.getPrincipal();

        SaleBySaleDetailsGetResponse response = service.findMySaleDetailsById(user, id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Create sale",
            responses = {
                    @ApiResponse(
                            description = "sale crated successfully",
                            responseCode = "201",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SalePostResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "the given product id was not found",
                            responseCode = "404",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = DefaultMessageError.class,
                                            example = """
                                                    {
                                                        "id": 404,
                                                        "message": "Product with id '999' not found"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "required field not given",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ArgumentNotValidMessageError.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "requesting user is not authenticated",
                            responseCode = "403",
                            content = @Content
                    )
            }
    )
    @PostMapping
    public ResponseEntity<SalePostResponse> create(Authentication authentication, @Valid @RequestBody SalePostRequest request) {
        User user = (User) authentication.getPrincipal();

        SalePostResponse response = service.create(user, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
