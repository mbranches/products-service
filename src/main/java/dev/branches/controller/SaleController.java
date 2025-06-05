package dev.branches.controller;

import dev.branches.dto.SaleGetResponse;
import dev.branches.dto.SalePostRequest;
import dev.branches.dto.SalePostResponse;
import dev.branches.service.SaleService;
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

    @PostMapping
    public ResponseEntity<SalePostResponse> create(Authentication authentication, @RequestBody SalePostRequest request) {
        String userLogin = (String) authentication.getPrincipal();

        SalePostResponse response = service.create(userLogin, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
