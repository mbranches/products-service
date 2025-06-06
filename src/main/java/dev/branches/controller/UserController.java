package dev.branches.controller;

import dev.branches.dto.response.UserGetResponse;
import dev.branches.dto.request.UserPostRequest;
import dev.branches.dto.response.UserPostResponse;
import dev.branches.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserGetResponse>> findAll() {
        List<UserGetResponse> response = service.findAll();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserPostResponse> createUser(@Valid @RequestBody UserPostRequest request) {
        UserPostResponse response = service.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
