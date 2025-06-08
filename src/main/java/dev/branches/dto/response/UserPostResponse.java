package dev.branches.dto.response;

import java.util.List;

public record UserPostResponse(String id, String firstName, String lastName, String login, String encryptedPassword, List<UserRoleByUserPostResponse> roles) {}