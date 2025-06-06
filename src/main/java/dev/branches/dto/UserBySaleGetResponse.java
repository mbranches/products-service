package dev.branches.dto;

import dev.branches.model.User;

public record UserBySaleGetResponse(String id, String firstName, String lastName) {
    public static UserBySaleGetResponse of(User user) {
        return new UserBySaleGetResponse(user.getId(), user.getFirstName(), user.getLastName());
    }
}
