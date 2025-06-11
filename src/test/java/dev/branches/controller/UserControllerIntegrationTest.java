package dev.branches.controller;

import dev.branches.config.IntegrationTest;
import dev.branches.utils.FileUtils;
import dev.branches.utils.UserUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@IntegrationTest
public class UserControllerIntegrationTest {
    private final String URL = "/api/v1/users";
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private FileUtils fileUtils;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "managerUser", authorities = "ROLE_MANAGER")
    @Test
    @DisplayName("GET /api/v1/user returns all user when successful")
    @Order(1)
    void findAll_ReturnsAllUsers_WhenSuccessful() throws Exception {
        mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/v1/user returns forbidden when then requesting user does not have at least manager role")
    @Order(2)
    void findAll_ReturnsForbidden_WhenTheRequestingUserDoesNotHaveAtLeastAdminRole() throws Exception {
        mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "adminUser", authorities = "ROLE_ADMIN")
    @Test
    @DisplayName("POST /api/v1/users creates user when successful")
    @Order(3)
    void create_CreatesUser_WhenSuccessful() throws Exception {
        String request = fileUtils.readResourceFile("user/post-request-user-200.json");

        mockMvc.perform(
                        post(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @WithMockUser(username = "managerUser", authorities = "ROLE_MANAGER")
    @Test
    @DisplayName("POST /api/v1/users returns forbidden when then requesting user does not have admin role")
    @Order(4)
    void create_ReturnsForbidden_WhenTheRequestingUserDoesNotHaveAdminRole() throws Exception {
        String request = fileUtils.readResourceFile("user/post-request-user-200.json");

        mockMvc.perform(
                        post(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "adminUser", authorities = "ROLE_ADMIN")
    @ParameterizedTest
    @MethodSource("postUserBadRequestSource")
    @DisplayName("create returns BadRequestException when the fields are invalid")
    @Order(5)
    void create_ReturnsBadRequestException_WhenTheFieldsAreInvalid(String requestFile, String expectedResponseFile) throws Exception {
        String request = fileUtils.readResourceFile("user/%s".formatted(requestFile));
        String expectedResponse = fileUtils.readResourceFile("user/%s".formatted(expectedResponseFile));

        mockMvc.perform(
                        post(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedResponse));
    }

    private static Stream<Arguments> postUserBadRequestSource() {
        return Stream.of(
                Arguments.of("post-request-user-empty-field-400.json", "post-response-user-empty-field-400.json"),
                Arguments.of("post-request-user-null-fields-400.json", "post-response-user-null-fields-400.json")
        );
    }
}
