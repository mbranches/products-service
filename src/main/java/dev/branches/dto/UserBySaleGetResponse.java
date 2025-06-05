package dev.branches.dto;

import dev.branches.model.User;

public record UserBySaleGetResponse(String login) {
    public static UserBySaleGetResponse of(User user) {
        return new UserBySaleGetResponse(user.getLogin());
    }
}
