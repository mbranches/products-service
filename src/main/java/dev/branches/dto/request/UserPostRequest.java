package dev.branches.dto.request;

import dev.branches.model.Role.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.With;

import java.util.List;

@With
public record UserPostRequest(
        @Schema(description = "user first name", example = "Marcus")
        @NotBlank(message = "The field 'firstName' is required")
        String firstName,
        @Schema(description = "user last name", example = "Branches")
        @NotBlank(message = "The field 'lastName' is required")
        String lastName,
        @Schema(description = "user login", example = "m.branches9")
        @NotBlank(message = "The field 'login' is required")
        String login,
        @Schema(description = "user password", example = "M_branches9@")
        @NotBlank(message = "The field 'password' is required")
        String password,
        @Schema(description = "user roles")
        @NotNull(message = "The field 'roles' is required")
        List<RoleType> roles
) {}
