package dev.branches.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginPostRequest(
        @NotBlank(message = "The field 'login' is required")
        String login,
        @NotBlank(message = "The field 'password' is required")
        String password
) {}
