package dev.branches.dto.response;

import dev.branches.model.Role.RoleType;

import java.util.List;

public record UserGetResponse(String id, String firstName, String lastName, String login, String encryptedPassword, List<RoleType> roles) {}
