package dev.branches.dto;

import dev.branches.model.User;

public record UserBySalePostResponse(String id, String login) {
    public static UserBySalePostResponse of(User user) {
        return new UserBySalePostResponse(user.getId(), user.getLogin());
    }
}
