package dev.branches.controller;

import dev.branches.config.IntegrationTest;
import dev.branches.model.*;
import dev.branches.repository.*;
import dev.branches.utils.FileUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
class SaleControllerIntegrationTest {
    private final String URL = "/api/v1/sales";
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private SaleRepository repository;
    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "user.manager", authorities = "ROLE_MANAGER")
    @Test
    @DisplayName("GET /api/v1/sales returns all sales when successful")
    @Order(1)
    void findAll_ReturnsAllSales_WhenSuccessful() throws Exception {
        mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user.customer", authorities = "ROLE_CUSTOMER")
    @Test
    @DisplayName("GET /api/v1/sales returns forbidden when the requesting user does not have at least manager role")
    @Order(2)
    void findAll_ReturnsForbidden_WhenTheRequestingUserDoesNotHaveAtLeastManagerRole() throws Exception {
        mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Sql(value = "classpath:sql/create-customer-user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/create-product.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/create-sale.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/clear-product.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:sql/clear-sale.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:sql/delete-customer-user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user.manager", authorities = "ROLE_MANAGER")
    @Test
    @DisplayName("GET /api/v1/sales/1/details returns sale details of the given id when successful")
    @Order(3)
    void findSaleDetailsById_ReturnsSaleDetailsOfTheGivenId_WhenSuccessful() throws Exception {
        Sale saleToBeFound = repository.findAll().getFirst();
        Long idToSearch = saleToBeFound.getId();

        mockMvc.perform(get(URL + "/%s/details".formatted(idToSearch)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user.manager", authorities = "ROLE_MANAGER")
    @Test
    @DisplayName("GET /api/v1/sales/999/details throws NotFoundException when the given id is not found")
    @Order(4)
    void findSaleDetailsById_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() throws Exception {
        fileUtils.readResourceFile("sale/get-response-sale-details-404.json");

        Long randomId = 999L;

        mockMvc.perform(get(URL + "/%s/details".formatted(randomId)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Sql(value = "classpath:sql/create-customer-user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/create-product.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/create-sale.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/clear-product.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:sql/clear-sale.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:sql/delete-customer-user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user.customer", authorities = "ROLE_CUSTOMER")
    @Test
    @DisplayName("GET /api/v1/sales/1/details returns forbidden when the requesting user does not have at least manager role")
    @Order(5)
    void findSaleDetailsById_ReturnsForbidden_WhenTheRequestingUserDoesNotHaveAtLeastManagerRole() throws Exception {
        Sale saleToBeFound = repository.findAll().getFirst();
        Long idToSearch = saleToBeFound.getId();

        mockMvc.perform(get(URL + "/%s/details".formatted(idToSearch)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Sql(value = "classpath:sql/create-customer-user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/create-product.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/create-sale.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/clear-product.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:sql/clear-sale.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:sql/delete-customer-user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails(value = "customer.user", userDetailsServiceBeanName = "userDetailsServiceImpl")
    @Test
    @DisplayName("GET /api/v1/sales/me returns found user sales when successful")
    @Order(6)
    void findMySales_ReturnsFoundUserSales_WhenSuccessful() throws Exception {
        mockMvc.perform(get(URL + "/me"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsServiceImpl")
    @Test
    @DisplayName("GET /api/v1/sales/me returns an empty when the requesting user does not have any sale")
    @Order(7)
    void findMySales_ReturnsEmptyList_WhenTheRequestingUserDoesNotHaveAnySale() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("sale/get-response-sales-empty-list-200.json");

        mockMvc.perform(get(URL + "/me"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /api/v1/sales/me returns Forbidden when the requesting user is not authenticated")
    @Order(8)
    void findMySales_ReturnsForbidden_WhenTheRequestingUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(get(URL + "/me"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Sql(value = "classpath:sql/create-customer-user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/create-product.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/create-sale.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/clear-product.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:sql/clear-sale.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:sql/delete-customer-user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails(value = "customer.user", userDetailsServiceBeanName = "userDetailsServiceImpl")
    @Test
    @DisplayName("GET /api/v1/sales/me/1/details returns sale details of the requesting user when successful")
    @Order(9)
    void findMySaleDetailsById_ReturnsSaleDetailsOfTheRequestingUser_WhenSuccessful() throws Exception {
        Sale saleToBeFound = repository.findAll().getFirst();
        Long idToSearch = saleToBeFound.getId();

        mockMvc.perform(get(URL + "/me/%s/details".formatted(idToSearch)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Sql(value = "classpath:sql/create-customer-user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/delete-customer-user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails(value = "customer.user", userDetailsServiceBeanName = "userDetailsServiceImpl")
    @Test
    @DisplayName("GET /api/v1/sales/me/999/details throws NotFoundException when the given id is not found")
    @Order(10)
    void findMySaleDetailsById_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("sale/get-response-sale-details-404.json");

        Long randomId = 999L;

        mockMvc.perform(get(URL + "/me/%s/details".formatted(randomId)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(expectedResponse));
    }

    @Sql(value = "classpath:sql/create-customer-user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/create-product.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/create-sale.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/clear-product.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:sql/clear-sale.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:sql/delete-customer-user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsServiceImpl")
    @Test
    @DisplayName("GET /api/v1/sales/me/1/details throws Forbidden when the found sale does not belong to the requesting user")
    @Order(11)
    void findMySaleDetailsById_ThrowsForbiddenException_WhenTheFoundSaleDoesNotBelongToTheRequestingUser() throws Exception {
        Sale saleToBeFound = repository.findAll().getFirst();
        Long idToSearch = saleToBeFound.getId();

        mockMvc.perform(get(URL + "/me/%s/details".formatted(idToSearch)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Sql(value = "classpath:sql/create-customer-user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/delete-customer-user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("GET /api/v1/sales/me/1/details throws Forbidden when the requesting user is not authenticated")
    @Order(12)
    void findMySaleDetailsById_ThrowsForbiddenException_WhenTheRequestingUserIsNotAuthenticated() throws Exception {
        Long idToSearch = 1L;

        mockMvc.perform(get(URL + "/me/%s/details".formatted(idToSearch)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Sql(value = "classpath:sql/create-customer-user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/create-product.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/clear-product.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:sql/clear-sale.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:sql/delete-customer-user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails(value = "customer.user", userDetailsServiceBeanName = "userDetailsServiceImpl")
    @Test
    @DisplayName("POST /api/v1/sales returns created sale when successful")
    @Order(13)
    void create_ReturnsCreatedSale_WhenSuccessful() throws Exception {
        Product product = productRepository.findAll().getFirst();

        String request = """
                {
                  "products": [
                    {
                      "productId": %s,
                      "quantity": 10
                    }
                  ]
                }
                """.formatted(product.getId());

        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .characterEncoding("UTF-8")
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Sql(value = "classpath:sql/create-customer-user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/delete-customer-user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails(value = "customer.user", userDetailsServiceBeanName = "userDetailsServiceImpl")
    @Test
    @DisplayName("POST /api/v1/sales throws NotFoundException when some given product id is not found")
    @Order(14)
    void create_ThrowsNotFoundException_WhenSomeGivenProductIdIsNotFound() throws Exception {
        String request = fileUtils.readResourceFile("sale/post-request-sale-invalid-product-200.json");
        String expectedResponse = fileUtils.readResourceFile("sale/post-response-sale-invalid-product-404.json");

        mockMvc.perform(
                        post(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                                .characterEncoding("UTF-8")
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(expectedResponse));
    }

    @Sql(value = "classpath:sql/create-product.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:sql/clear-product.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("POST /api/v1/sales throws NotFoundException when the requesting user is not authenticated")
    @Order(15)
    void create_ThrowsForbidden_WhenTheRequestingUserIsNotAuthenticated() throws Exception {
        Product product = productRepository.findAll().getFirst();

        String request = """
                {
                  "products": [
                    {
                      "productId": %s,
                      "quantity": 10
                    }
                  ]
                }
                """.formatted(product.getId());

        mockMvc.perform(
                        post(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                                .characterEncoding("UTF-8")
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}