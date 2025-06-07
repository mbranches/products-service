package dev.branches.service;

import dev.branches.model.Role;
import dev.branches.model.Role.RoleType;
import dev.branches.repository.RoleRepository;
import dev.branches.utils.RoleUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    @InjectMocks
    private RoleService service;
    @Mock
    private RoleRepository repository;
    private List<Role> roleList;

    @BeforeEach
    void init() {
        roleList = RoleUtils.newRoleList();
    }

    @Test
    @DisplayName("findByName returns foundRole when successful")
    void findByName_ReturnsFoundRole_WhenSuccessful() {
        Role roleToBeFound = roleList.getFirst();
        RoleType nameToSearch = roleToBeFound.getName();

        BDDMockito.when(repository.findByName(nameToSearch)).thenReturn(roleToBeFound);

        Role response = service.findByName(nameToSearch);

        assertThat(response)
                .isNotNull()
                .isEqualTo(roleToBeFound);
    }
}