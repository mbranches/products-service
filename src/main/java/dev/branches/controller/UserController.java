package dev.branches.controller;

import dev.branches.dto.UserGetResponse;
import dev.branches.dto.UserPostRequest;
import dev.branches.dto.UserPostResponse;
import dev.branches.service.UserService;
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
    public ResponseEntity<UserPostResponse> createUser(@RequestBody UserPostRequest request) {
        UserPostResponse response = service.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
