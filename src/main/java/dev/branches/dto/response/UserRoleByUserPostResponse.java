package dev.branches.dto.response;

import dev.branches.model.Role.RoleType;
import dev.branches.model.UserRole;

public record UserRoleByUserPostResponse(RoleType role, String description) {}
