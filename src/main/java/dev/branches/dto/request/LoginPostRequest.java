package dev.branches.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.With;

@With
public record LoginPostRequest(
        @NotBlank(message = "The field 'login' is required")
        String login,
        @NotBlank(message = "The field 'password' is required")
        String password
) {}
