package dev.branches.controller;

import dev.branches.config.IntegrationTest;
import dev.branches.model.Product;
import dev.branches.repository.ProductRepository;
import dev.branches.utils.FileUtils;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerIntegrationTest {
    private final String URL = "/api/v1/products";
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository repository;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private FileUtils fileUtils;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        Product product = Product.builder().name("Mesa com 4 cadeiras").unitPrice(3500D).build();

        repository.save(product);
    }

    @AfterEach
    void finish() {
        repository.deleteAll();
    }

    @WithMockUser(username = "managerUser", authorities = "ROLE_MANAGER")
    @Test
    @DisplayName("GET /api/v1/products returns all products when successful")
    @Order(1)
    void findAll_ReturnsAllProducts_WhenSuccessful() throws Exception {
        mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/v1/products returns forbidden when then requesting user is not authenticated")
    @Order(2)
    void findAll_ReturnsForbidden_WhenTheRequestingUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "adminUser", authorities = "ROLE_ADMIN")
    @Test
    @DisplayName("POST /api/v1/products creates product when successful")
    @Order(3)
    void create_CreatesProduct_WhenSuccessful() throws Exception {
        String request = fileUtils.readResourceFile("product/post-request-product-200.json");

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
    @DisplayName("POST /api/v1/products returns forbidden when then requesting user does not have admin role")
    @Order(4)
    void create_ReturnsForbidden_WhenTheRequestingUserDoesNotHaveAdminRole() throws Exception {
        String request = fileUtils.readResourceFile("product/post-request-product-200.json");

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
    @MethodSource("postProductBadRequestSource")
    @DisplayName("create returns BadRequestException when the fields are invalid")
    @Order(5)
    void create_ReturnsBadRequestException_WhenTheFieldsAreInvalid(String requestFile, String expectedResponseFile) throws Exception {
        String request = fileUtils.readResourceFile("product/%s".formatted(requestFile));
        String expectedResponse = fileUtils.readResourceFile("product/%s".formatted(expectedResponseFile));

        mockMvc.perform(
                        post(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedResponse));
    }

    private static Stream<Arguments> postProductBadRequestSource() {
        return Stream.of(
                Arguments.of("post-request-product-empty-field-400.json", "post-response-product-empty-field-400.json"),
                Arguments.of("post-request-product-null-fields-400.json", "post-response-product-null-fields-400.json")
        );
    }
}
