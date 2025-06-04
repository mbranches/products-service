package dev.branches.service;

import dev.branches.model.Role;
import dev.branches.model.Role.RoleType;
import dev.branches.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    public Role findByName(RoleType name) {
        return repository.findByName(name);
    }
}
