package dev.branches.dto.request;

import dev.branches.model.Role.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.With;

import java.util.List;

@With
public record UserPostRequest(
        @NotBlank(message = "The field 'firstName' is required")
        String firstName,
        @NotBlank(message = "The field 'lastName' is required")
        String lastName,
        @NotBlank(message = "The field 'login' is required")
        String login,
        @NotBlank(message = "The field 'password' is required")
        String password,
        @NotNull(message = "The field 'roles' is required")
        List<RoleType> roles
) {}
