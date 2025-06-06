package dev.branches.controller;

import dev.branches.dto.LoginPostRequest;
import dev.branches.dto.LoginPostResponse;
import dev.branches.dto.RegisterPostRequest;
import dev.branches.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService service;

    @PostMapping("/login")
    public ResponseEntity<LoginPostResponse> login(@Valid @RequestBody LoginPostRequest request) {
        LoginPostResponse response = service.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterPostRequest request) {
        service.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
