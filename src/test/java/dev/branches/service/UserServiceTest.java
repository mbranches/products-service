package dev.branches.service;

import dev.branches.dto.request.LoginPostRequest;
import dev.branches.dto.request.RegisterPostRequest;
import dev.branches.dto.request.UserPostRequest;
import dev.branches.dto.response.LoginPostResponse;
import dev.branches.dto.response.UserGetResponse;
import dev.branches.dto.response.UserPostResponse;
import dev.branches.exception.BadRequestException;
import dev.branches.infra.security.JwtTokenService;
import dev.branches.mapper.UserMapper;
import dev.branches.model.Role;
import dev.branches.model.Role.RoleType;
import dev.branches.model.User;
import dev.branches.repository.UserRepository;
import dev.branches.utils.RoleUtils;
import dev.branches.utils.UserUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenService jwtTokenService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Authentication authentication;
    @Mock
    private RoleService roleService;
    private List<User> userList;
    private List<UserGetResponse> userGetResponseList;

    @BeforeEach
    void init() {
        userList = UserUtils.newUserList();
        userGetResponseList = UserUtils.newUserGetResponseList();
    }

    @Test
    @DisplayName("findAll returns all users when successful")
    @Order(1)
    void findAll_ReturnsAllUsers_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(userList);
        BDDMockito.when(mapper.toUserGetResponseList(userList)).thenReturn(userGetResponseList);

        List<UserGetResponse> response = service.findAll();

        assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(userGetResponseList);
    }

    @Test
    @DisplayName("login returns jwt token when successful")
    @Order(2)
    void login_ReturnsJwtToken_WhenSuccessful() {
        User user = userList.getFirst();

        LoginPostRequest postRequest = UserUtils.newLoginPostRequest();
        LoginPostResponse postResponse = UserUtils.newLoginPostResponse();

        BDDMockito.when(authenticationManager.authenticate(BDDMockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        BDDMockito.when(authentication.getPrincipal()).thenReturn(user);
        BDDMockito.when(jwtTokenService.generateKey(user)).thenReturn(postResponse.token());

        LoginPostResponse response = service.login(postRequest);

        assertThat(response)
                .isNotNull()
                .isEqualTo(postResponse);
    }

    @Test
    @DisplayName("registerUser creates customer user when successful")
    @Order(3)
    void registerUser_CreatesCustomerUser_WhenSuccessful() {
        RegisterPostRequest postRequest = UserUtils.newRegisterPostRequest();

        RoleType customer = RoleType.CUSTOMER;
        Role customerRole = RoleUtils.newRoleList().get(1);

        User userToRegister = UserUtils.newUserToRegister();
        User userRegistered = UserUtils.newUserRegistered();

        BDDMockito.when(repository.findByLogin(postRequest.login())).thenReturn(Optional.empty());
        BDDMockito.when(roleService.findByName(customer)).thenReturn(customerRole);
        BDDMockito.when(repository.save(userToRegister)).thenReturn(userRegistered);

        assertThatNoException()
                .isThrownBy(() -> service.registerUser(postRequest));
    }

    @Test
    @DisplayName("registerUser throws BadRequestException when the given login is already registered")
    @Order(4)
    void registerUser_ThrowsBadRequestException_WhenTheGivenLoginIsAlreadyRegistered() {
        User userLoginOwner = userList.getFirst();

        RegisterPostRequest postRequest = UserUtils.newRegisterPostRequest().withLogin(userLoginOwner.getLogin());

        BDDMockito.when(repository.findByLogin(postRequest.login())).thenReturn(Optional.of(userLoginOwner));

        assertThatThrownBy(() -> service.registerUser(postRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Login is already registered");
    }

    @Test
    @DisplayName("createUser creates user when successful")
    @Order(5)
    void createUser_CreatesUser_WhenSuccessful() {
        UserPostRequest postRequest = UserUtils.newUserPostRequest();
        UserPostResponse postResponse = UserUtils.newUserPostResponse();

        User userToSave = UserUtils.newUserToSave();
        User savedUser = UserUtils.newUserSaved();

        BDDMockito.when(repository.findByLogin(postRequest.login())).thenReturn(Optional.empty());
        BDDMockito.when(roleService.findByName(postRequest.roles().getFirst())).thenReturn(userToSave.getRoles().getFirst().getRole());
        BDDMockito.when(repository.save(userToSave)).thenReturn(savedUser);
        BDDMockito.when(mapper.toUserPostResponse(savedUser)).thenReturn(postResponse);

        UserPostResponse response = service.createUser(postRequest);

        assertThat(response)
                .isNotNull()
                .isEqualTo(postResponse);
    }

    @Test
    @DisplayName("createUser throws BadRequestException when the given login is already registered")
    @Order(6)
    void createUser_ThrowsBadRequestException_WhenTheGivenLoginIsAlreadyRegistered() {
        User userLoginOwner = userList.getFirst();

        UserPostRequest postRequest = UserUtils.newUserPostRequest().withLogin(userLoginOwner.getLogin());

        BDDMockito.when(repository.findByLogin(postRequest.login())).thenReturn(Optional.of(userLoginOwner));

        assertThatThrownBy(() -> service.createUser(postRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Login is already registered");
    }
}