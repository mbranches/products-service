package dev.branches.utils;

import dev.branches.dto.request.LoginPostRequest;
import dev.branches.dto.request.RegisterPostRequest;
import dev.branches.dto.request.UserPostRequest;
import dev.branches.dto.response.*;
import dev.branches.model.Role;
import dev.branches.model.Role.RoleType;
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

    public static List<UserGetResponse> newUserGetResponseList() {
        return newUserList().stream().map(user -> {
            List<RoleType> roles = user.getRoles().stream().map(userRole -> userRole.getRole().getName()).toList();

            return new UserGetResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getLogin(), user.getPassword(), roles);
        }).toList();
    }

    public static LoginPostRequest newLoginPostRequest() {
        User user = newUserList().getFirst();

        return new LoginPostRequest(user.getLogin(), "123");
    }

    public static LoginPostResponse newLoginPostResponse() {
        return new LoginPostResponse("meu-token");
    }

    public static UserBySaleGetResponse newUserBySaleGetResponse() {
        User user = newUserList().getFirst();

        return new UserBySaleGetResponse(user.getId(), user.getFirstName(), user.getLastName());
    }

    public static UserBySalePostResponse newUserBySalePostResponse() {
        User user = newUserList().getFirst();

        return new UserBySalePostResponse(user.getId(), user.getFirstName(), user.getLastName());
    }

    public static User newUserToRegister() {
        RegisterPostRequest postRequest = newRegisterPostRequest();
        Role role = RoleUtils.newRoleList().get(1);

        User user = User.builder()
                .firstName(postRequest.firstName())
                .lastName(postRequest.lastName())
                .login(postRequest.login())
                .password("encryptedPassword44##")
                .build();

        UserRole userRole = UserRole.builder().user(user).role(role).build();
        user.setRoles(List.of(userRole));

        return user;
    }

    public static User newUserRegistered() {
        User userToRegister = newUserToRegister();
        userToRegister.getRoles().forEach(userRole -> userRole.setId(4L));

        return userToRegister.withId("id4--121");
    }

    public static RegisterPostRequest newRegisterPostRequest() {
        return new RegisterPostRequest("João", "Vieira", "jvieira", "j123");
    }

    public static User newUserToSave() {
        UserPostRequest postRequest = newUserPostRequest();
        Role managerRole = RoleUtils.newRoleList().getLast();

        User userToSave = User.builder()
                .firstName(postRequest.firstName())
                .lastName(postRequest.lastName())
                .login(postRequest.login())
                .password("encryptedPassword4231")
                .build();

        List<UserRole> userRoleList = postRequest.roles().stream().map(roleType ->
                UserRole.builder().user(userToSave).role(managerRole).build()
        ).toList();

        return userToSave.withRoles(userRoleList);
    }

    public static User newUserSaved() {
        User userToSave = newUserToSave();
        userToSave.getRoles().forEach(userRole -> userRole.setId(4L));
        return userToSave.withId("idaa$#kfnai193eu");
    }

    public static UserPostRequest newUserPostRequest() {
        return new UserPostRequest("Mateus", "Lessa", "mat_lessa", "123", List.of(RoleType.MANAGER));
    }

    public static UserPostResponse newUserPostResponse() {
        User savedUser = newUserSaved();

        List<UserRoleByUserPostResponse> userRoleDtoList = savedUser.getRoles().stream().map(userRole -> {
            Role role = userRole.getRole();

            return new UserRoleByUserPostResponse(role.getName(), role.getDescription());
        }).toList();

        return new UserPostResponse(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getLogin(),
                savedUser.getPassword(),
                userRoleDtoList
        );
    }
}
