package dev.branches.utils;

import dev.branches.dto.response.UserBySaleGetResponse;
import dev.branches.model.Role;
import dev.branches.model.User;
import dev.branches.model.UserRole;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {
    public static List<User> newUserList() {
        User user1 = User.builder().id("1").firstName("Marcus").lastName("Branches").login("marcus.branches").password("ha#311iaudjianfih91f").build();
        Role role1 = RoleUtils.newRoleList().getFirst();
        UserRole userRole1 = UserRole.builder().id(1L).user(user1).role(role1).build();
        user1.setRoles(List.of(userRole1));

        User user2 = User.builder().id("2").firstName("Vinicius").lastName("Lima").login("vinicius_lima").password("bai$&f1nfih92f").build();
        Role role2 = RoleUtils.newRoleList().get(1);
        UserRole userRole2 = UserRole.builder().id(2L).user(user2).role(role2).build();
        user2.setRoles(List.of(userRole2));

        User user3 = User.builder().id("3").firstName("Lucas").lastName("Matteo").login("vinicius_lima").password("afjkafnj121P$#").build();
        Role role3 = RoleUtils.newRoleList().get(1);
        UserRole userRole3 = UserRole.builder().id(3L).user(user3).role(role3).build();
        user3.setRoles(List.of(userRole3));

        return new ArrayList<>(List.of(user1, user2, user3));
    }

    public static UserBySaleGetResponse newUserBySaleGetResponse() {
        User user = newUserList().getFirst();

        return new UserBySaleGetResponse(user.getId(), user.getFirstName(), user.getLastName());
    }
}
