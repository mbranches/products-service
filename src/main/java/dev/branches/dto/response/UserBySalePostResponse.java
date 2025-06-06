package dev.branches.dto.response;

import dev.branches.model.User;

public record UserBySalePostResponse(String id, String firstName, String lastName) {
    public static UserBySalePostResponse of(User user) {
        return new UserBySalePostResponse(user.getId(), user.getFirstName(), user.getLastName());
    }
}
