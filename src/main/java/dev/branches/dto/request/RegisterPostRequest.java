package dev.branches.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterPostRequest(
        @NotBlank(message = "The field 'firstName' is required")
        String firstName,
        @NotBlank(message = "The field 'lastName' is required")
        String lastName,
        @NotBlank(message = "The field 'login' is required")
        String login,
        @NotBlank(message = "The field 'password' is required")
        String password
) {}
