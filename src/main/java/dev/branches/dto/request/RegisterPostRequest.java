package dev.branches.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.With;

@With
public record RegisterPostRequest(
        @Schema(description = "user first name", example = "Vinicius")
        @NotBlank(message = "The field 'firstName' is required")
        String firstName,
        @Schema(description = "user last name", example = "Lima")
        @NotBlank(message = "The field 'lastName' is required")
        String lastName,
        @Schema(description = "user login", example = "v.Lima87")
        @NotBlank(message = "The field 'login' is required")
        String login,
        @Schema(description = "user password", example = "minhaSenhaSegura3_")
        @NotBlank(message = "The field 'password' is required")
        String password
) {}
