package dev.branches.dto.response;

import dev.branches.model.Role.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserRoleByUserPostResponse(
        @Schema(description = "user role name", example = "ADMIN")
        RoleType role,
        @Schema(description = "user role description", example = "has all system accesses")
        String description
) {}
