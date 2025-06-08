package dev.branches.mapper;

import dev.branches.dto.response.UserGetResponse;
import dev.branches.dto.response.UserPostResponse;
import dev.branches.dto.response.UserRoleByUserPostResponse;
import dev.branches.model.Role;
import dev.branches.model.Role.RoleType;
import dev.branches.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    List<UserGetResponse> toUserGetResponseList(List<User> userList);

    default UserGetResponse toUserGetResponse(User user) {
        List<RoleType> roles = user.getRoles().stream().map(userRole -> userRole.getRole().getName()).toList();

        return new UserGetResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getLogin(), user.getPassword(), roles);
    }

    default UserPostResponse toUserPostResponse(User user) {
        List<UserRoleByUserPostResponse> roles = user.getRoles().stream().map(userRole -> {
            Role role = userRole.getRole();
            return new UserRoleByUserPostResponse(role.getName(), role.getDescription());
        }).toList();

        return new UserPostResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getLogin(), user.getPassword(), roles);
    }
}
