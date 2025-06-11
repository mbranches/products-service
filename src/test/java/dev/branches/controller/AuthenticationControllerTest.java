package dev.branches.controller;

import dev.branches.config.IntegrationTest;
import dev.branches.model.Role;
import dev.branches.model.Role.RoleType;
import dev.branches.model.User;
import dev.branches.model.UserRole;
import dev.branches.repository.RoleRepository;
import dev.branches.repository.UserRepository;
import dev.branches.utils.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@IntegrationTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationControllerTest {
    private final String URL = "/api/v1/auth";
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private FileUtils fileUtils;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        Role managerRole = roleRepository.findByName(RoleType.MANAGER);

        User user = new User();
        user.setFirstName("User");
        user.setLastName("Manager");
        user.setLogin("manager.user");
        user.setPassword(passwordEncoder.encode("1245"));
        UserRole userRole = UserRole.builder().user(user).role(managerRole).build();
        user.setRoles(List.of(userRole));

        repository.save(user);
    }

    @AfterEach
    void finish() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("login returns jwt token when successful")
    @Order(1)
    void login_ReturnsJwtToken_WhenSuccessful() throws Exception {
        String request = fileUtils.readResourceFile("auth/post-request-login-200.json");

        mockMvc.perform(
                post(URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("login throws Unauthorized when the given login or password is invalid")
    @Order(2)
    void login_ThrowsUnauthorized_WhenTheGivenLoginOrPasswordIsInvalid() throws Exception {
        String request = fileUtils.readResourceFile("auth/post-request-invalid-login-200.json");
        String expectedResponse = fileUtils.readResourceFile("auth/post-response-invalid-login-401.json");

        mockMvc.perform(
                        post(URL + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(expectedResponse));
    }

    @ParameterizedTest
    @MethodSource("postLoginBadRequestSource")
    @DisplayName("login throws BadRequestException when the fields are invalid")
    @Order(3)
    void login_ReturnsBadRequestException_WhenTheFieldsAreInvalid(String requestFile, String expectedResponseFile) throws Exception {
        String request = fileUtils.readResourceFile("auth/%s".formatted(requestFile));
        String expectedResponse = fileUtils.readResourceFile("auth/%s".formatted(expectedResponseFile));

        mockMvc.perform(
                        post(URL + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedResponse));
    }

    private static Stream<Arguments> postLoginBadRequestSource() {
        return Stream.of(
                Arguments.of("post-request-login-empty-fields-400.json", "post-response-login-empty-fields-400.json"),
                Arguments.of("post-request-login-null-fields-400.json", "post-response-login-null-fields-400.json")
        );
    }

    @Test
    @DisplayName("register registers user when successful")
    @Order(4)
    void register_RegistersUser_WhenSuccessful() throws Exception {
        String request = fileUtils.readResourceFile("auth/post-request-register-200.json");

        mockMvc.perform(
                        post(URL + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("register throws BadRequestException when the given login is already registered")
    @Order(5)
    void register_ThrowsBadRequestException_WhenTheGivenLoginIsAlreadyRegistered() throws Exception {
        String request = fileUtils.readResourceFile("auth/post-request-register-existing-login-200.json");
        String expectedResponse = fileUtils.readResourceFile("auth/post-response-register-existing-login-400.json");

        mockMvc.perform(
                        post(URL + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedResponse));
    }

    @ParameterizedTest
    @MethodSource("postRegisterBadRequestSource")
    @DisplayName("register throws BadRequestException when the fields are invalid")
    @Order(6)
    void register_ReturnsBadRequestException_WhenTheFieldsAreInvalid(String requestFile, String expectedResponseFile) throws Exception {
        String request = fileUtils.readResourceFile("auth/%s".formatted(requestFile));
        String expectedResponse = fileUtils.readResourceFile("auth/%s".formatted(expectedResponseFile));

        mockMvc.perform(
                        post(URL + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedResponse));
    }

    private static Stream<Arguments> postRegisterBadRequestSource() {
        return Stream.of(
                Arguments.of("post-request-register-empty-fields-400.json", "post-response-register-empty-fields-400.json"),
                Arguments.of("post-request-register-null-fields-400.json", "post-response-register-null-fields-400.json")
        );
    }
}