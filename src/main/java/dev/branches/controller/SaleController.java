package dev.branches.controller;

import dev.branches.dto.response.SaleBySaleDetailsGetResponse;
import dev.branches.dto.response.SaleGetResponse;
import dev.branches.dto.request.SalePostRequest;
import dev.branches.dto.response.SalePostResponse;
import dev.branches.model.User;
import dev.branches.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService service;

    @GetMapping
    public ResponseEntity<List<SaleGetResponse>> findAll() {
        List<SaleGetResponse> response = service.findAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<SaleBySaleDetailsGetResponse> findSaleDetailsById(@PathVariable Long id) {
        SaleBySaleDetailsGetResponse response = service.findSaleDetailsById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<SaleGetResponse>> findMySales(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        List<SaleGetResponse> response = service.findAllByUser(user);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me/{id}/details")
    public ResponseEntity<SaleBySaleDetailsGetResponse> findMySaleDetailsById(Authentication authentication, @PathVariable Long id) {
        User user = (User) authentication.getPrincipal();

        SaleBySaleDetailsGetResponse response = service.findMySaleDetailsById(user, id);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SalePostResponse> create(Authentication authentication, @Valid @RequestBody SalePostRequest request) {
        User user = (User) authentication.getPrincipal();

        SalePostResponse response = service.create(user, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
