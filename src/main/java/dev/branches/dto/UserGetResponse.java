package dev.branches.dto;

import dev.branches.model.Role.RoleType;
import dev.branches.model.User;

import java.util.List;

public record UserGetResponse(String id, String name, String lastName, String login, String encryptedPassword, List<RoleType> roles) {
    public static UserGetResponse of(User user) {
        List<RoleType> roles = user.getRoles().stream().map(userRole -> userRole.getRole().getName()).toList();

        return new UserGetResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getLogin(), user.getPassword(), roles);
    }
}
