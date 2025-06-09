package dev.branches.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.With;

@With
public record LoginPostRequest(
        @Schema(description = "user login", example = "admin")
        @NotBlank(message = "The field 'login' is required")
        String login,
        @Schema(description = "user password", example = "admin")
        @NotBlank(message = "The field 'password' is required")
        String password
) {}
