package dev.branches.utils;

import dev.branches.model.Role;
import dev.branches.model.Role.RoleType;

import java.util.ArrayList;
import java.util.List;

public class RoleUtils {
    public static List<Role> newRoleList() {
        RoleType adminRole = RoleType.ADMIN;
        RoleType customerRole = RoleType.CUSTOMER;
        RoleType managerRole = RoleType.MANAGER;

        Role role1 = Role.builder().id(1L).name(adminRole).description("has all system accesses").build();
        Role role2 = Role.builder().id(2L).name(customerRole).description("has limited system accesses").build();
        Role role3 = Role.builder().id(3L).name(managerRole).description("has limited access to administrative and management functionalities").build();

        return new ArrayList<>(List.of(role1, role2, role3));
    }
}
