package dev.branches.dto;

import dev.branches.model.Role.RoleType;

import java.util.List;

public record UserPostRequest(String firstName, String lastName, String login, String password, List<RoleType> roles) {
}
