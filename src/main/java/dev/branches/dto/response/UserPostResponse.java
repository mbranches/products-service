package dev.branches.dto.response;

import dev.branches.model.User;

import java.util.Set;
import java.util.stream.Collectors;

public record UserPostResponse(String id, String firstName, String lastName, String login, String encryptedPassword, Set<UserRoleByUserPostResponse> roles) {
    public static UserPostResponse of(User user) {
        Set<UserRoleByUserPostResponse> roles = user.getRoles().stream().map(UserRoleByUserPostResponse::of).collect(Collectors.toSet());

        return new UserPostResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getLogin(), user.getPassword(), roles);
    }
}
