package dev.branches.config;

import dev.branches.model.Role;
import dev.branches.model.Role.RoleType;
import dev.branches.model.User;
import dev.branches.model.UserRole;
import dev.branches.repository.UserRepository;
import dev.branches.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class AdminUserConfig implements CommandLineRunner {
    private final RoleService roleService;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = roleService.findByName(RoleType.ADMIN);

        if (repository.findByLogin("admin").isPresent()) return;

        User user = new User();
        user.setLogin("admin");
        user.setPassword(passwordEncoder.encode("admin"));

        UserRole userRole = UserRole.builder().user(user).role(adminRole).build();

        user.setRoles(List.of(userRole));

        repository.save(user);
    }
}
