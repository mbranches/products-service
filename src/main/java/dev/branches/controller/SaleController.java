package dev.branches.controller;

import dev.branches.dto.SaleGetResponse;
import dev.branches.dto.SalePostRequest;
import dev.branches.dto.SalePostResponse;
import dev.branches.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SalePostResponse> create(@RequestBody SalePostRequest postRequest) {
        SalePostResponse response = service.create(postRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
