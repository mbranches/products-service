package dev.branches.service;

import dev.branches.dto.request.SalePostRequest;
import dev.branches.dto.request.SaleProductBySalePostRequest;
import dev.branches.dto.response.SaleBySaleDetailsGetResponse;
import dev.branches.dto.response.SaleGetResponse;
import dev.branches.dto.response.SalePostResponse;
import dev.branches.exception.NotFoundException;
import dev.branches.mapper.SaleMapper;
import dev.branches.model.Product;
import dev.branches.model.Sale;
import dev.branches.model.SaleProduct;
import dev.branches.model.User;
import dev.branches.repository.SaleRepository;
import dev.branches.utils.ProductUtils;
import dev.branches.utils.SaleProductUtils;
import dev.branches.utils.SaleUtils;
import dev.branches.utils.UserUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class SaleServiceTest {
    @InjectMocks
    private SaleService service;
    @Mock
    private SaleProductService saleProductService;
    @Mock
    private ProductService productService;
    @Mock
    private SaleRepository repository;
    @Mock
    private SaleMapper mapper;
    private List<Sale> saleList;
    private List<SaleGetResponse> saleGetResponseList;

    @BeforeEach
    void init() {
        saleList = SaleUtils.newSaleList();
        saleGetResponseList = SaleUtils.newSaleGetResponseList();
    }

    @Test
    @DisplayName("findAll returns all sales when successful")
    @Order(1)
    void findAll_ReturnsAllSales_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(saleList);
        BDDMockito.when(mapper.toSaleGetResponseList(saleList)).thenReturn(saleGetResponseList);

        List<SaleGetResponse> response = service.findAll();

        assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(saleGetResponseList);
    }
    @Test
    @DisplayName("findAllByUser returns found sales when successful")
    @Order(2)
    void findAllByUser_ReturnsFoundSales_WhenSuccessful() {
        User userToSearch = UserUtils.newUserList().getFirst();

        BDDMockito.when(repository.findAllByUser(userToSearch)).thenReturn(saleList);
        BDDMockito.when(mapper.toSaleGetResponseList(saleList)).thenReturn(saleGetResponseList);

        List<SaleGetResponse> response = service.findAllByUser(userToSearch);

        assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(saleGetResponseList);

    }

    @Test
    @DisplayName("findAllByUser returns an empty list when the given user does not have any sale")
    @Order(3)
    void findAllByUser_ReturnsEmptyList_WhenTheGivenUserDoesNotHaveAnySale() {
        User userToSearch = UserUtils.newUserList().getLast();

        BDDMockito.when(repository.findAllByUser(userToSearch)).thenReturn(Collections.emptyList());
        BDDMockito.when(mapper.toSaleGetResponseList(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<SaleGetResponse> response = service.findAllByUser(userToSearch);

        assertThat(response)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findSaleDetailsById returns details of the found sale when successful")
    @Order(4)
    void findSaleDetailsById_ReturnsSaleDetailsOfTheGivenId_WhenSuccessful() {
        Sale sale = saleList.getFirst();
        Long idToSearch = sale.getId();

        List<SaleProduct> foundSaleProducts = Collections.singletonList(SaleProductUtils.newSaleProductExisting());
        SaleBySaleDetailsGetResponse expectedResponse = SaleUtils.newSaleBySaleDetailsGetResponse();

        BDDMockito.when(repository.findById(idToSearch)).thenReturn(Optional.of(sale));
        BDDMockito.when(saleProductService.findAllBySaleId(idToSearch)).thenReturn(foundSaleProducts);
        BDDMockito.when(mapper.toSaleBySaleDetailsGetResponse(sale, foundSaleProducts)).thenReturn(expectedResponse);

        SaleBySaleDetailsGetResponse response = service.findSaleDetailsById(idToSearch);

        assertThat(response)
                .isNotNull()
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("findSaleDetailsById throws NotFoundException when the given id is not found")
    @Order(5)
    void findSaleDetailsById_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() {
        long randomId = 999L;
        assertThatThrownBy(() -> service.findSaleDetailsById(randomId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Sale with id '%s' not found".formatted(randomId));
    }

    @Test
    @DisplayName("findMySaleDetailsById returns sale details of the given id when the found sale belongs to the given user")
    @Order(6)
    void findMySaleDetailsById_ReturnsSaleDetailsOfTheGivenId_WhenTheFoundSaleBelongsToTheGivenUser() {
        Sale sale = saleList.getFirst();
        User user = sale.getUser();
        Long idToSearch = sale.getId();

        List<SaleProduct> foundSaleProducts = Collections.singletonList(SaleProductUtils.newSaleProductExisting());
        SaleBySaleDetailsGetResponse expectedResponse = SaleUtils.newSaleBySaleDetailsGetResponse();

        BDDMockito.when(repository.findById(idToSearch)).thenReturn(Optional.of(sale));
        BDDMockito.when(saleProductService.findAllBySaleId(idToSearch)).thenReturn(foundSaleProducts);
        BDDMockito.when(mapper.toSaleBySaleDetailsGetResponse(sale, foundSaleProducts)).thenReturn(expectedResponse);

        SaleBySaleDetailsGetResponse response = service.findMySaleDetailsById(user, idToSearch);

        assertThat(response)
                .isNotNull()
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("findMySaleDetailsById throws NotFoundException when the given id is not found")
    @Order(7)
    void findMySaleDetailsById_ThrowsNotFoundException_WhenGivenIdIsNotFound() {
        User user = UserUtils.newUserList().getFirst();
        Long randomId = 999L;

        BDDMockito.when(repository.findById(randomId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findMySaleDetailsById(user, randomId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Sale with id '%s' not found".formatted(randomId));
    }

    @Test
    @DisplayName("findMySaleDetailsById throws Forbidden exception when the found sale does not belong to the given user")
    @Order(8)
    void findMySaleDetailsById_ThrowsForbiddenException_WhenTheFoundSaleDoesNotBelongToTheGivenUser() {
        Sale sale = saleList.getFirst();
        User randomUser = UserUtils.newUserList().getLast();
        Long idToSearch = sale.getId();

        BDDMockito.when(repository.findById(idToSearch)).thenReturn(Optional.of(sale));

        assertThatThrownBy(() -> service.findMySaleDetailsById(randomUser, idToSearch))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("findByIdOrThrowsNotFoundException returns found sale when successful")
    @Order(9)
    void findByIdOrThrowsNotFoundException_ReturnsFoundSale_WhenSuccessful() {
        Sale saleToBeFound = saleList.getFirst();
        Long idToSearch = saleToBeFound.getId();

        BDDMockito.when(repository.findById(idToSearch)).thenReturn(Optional.of(saleToBeFound));

        Sale response = service.findByIdOrThrowsNotFoundException(idToSearch);

        assertThat(response)
                .isNotNull()
                .isEqualTo(saleToBeFound);
    }

    @Test
    @DisplayName("findByIdOrThrowsNotFoundException throws NotFoundException when the given id is not found")
    @Order(10)
    void findByIdOrThrowsNotFoundException_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() {
        Long randomId = 999L;

        BDDMockito.when(repository.findById(randomId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findByIdOrThrowsNotFoundException(randomId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Sale with id '%s' not found".formatted(randomId));
    }

    @Test
    @DisplayName("create returns created sale when successful")
    @Order(11)
    void create_ReturnsCreatedSale_WhenSuccessful() {
        User user = UserUtils.newUserList().getFirst();

        SalePostRequest postRequest = SaleUtils.newSalePostRequest();
        SalePostResponse postResponse = SaleUtils.newSalePostResponse();

        Sale saleToSave = SaleUtils.newSaleToSave();
        Sale savedSale = SaleUtils.newSaleSaved();

        Product product = ProductUtils.newProductList().getFirst();

        SaleProductBySalePostRequest saleProductBySalePostRequest = postRequest.products().getFirst();

        SaleProduct saleProductToSave = SaleProduct.builder().product(product).quantity(saleProductBySalePostRequest.quantity()).build();
        SaleProduct savedSaleProduct = saleProductToSave.withId(4L);
        List<SaleProduct> savedSaleProductList = Collections.singletonList(savedSaleProduct);

        BDDMockito.when(productService.findByIdOrThrowsNotFoundException(saleProductBySalePostRequest.productId())).thenReturn(product);
        BDDMockito.when(repository.save(saleToSave)).thenReturn(savedSale);
        BDDMockito.when(saleProductService.saveAll(Collections.singletonList(saleProductToSave))).thenReturn(savedSaleProductList);
        BDDMockito.when(mapper.toSalePostResponse(savedSale, savedSaleProductList)).thenReturn(postResponse);

        SalePostResponse response = service.create(user, postRequest);

        assertThat(response)
                .isNotNull()
                .isEqualTo(postResponse);
    }

    @Test
    @DisplayName("create throws NotFoundException when some given product id is not found")
    @Order(12)
    void create_ThrowsNotFoundException_WhenSomeGivenProductIdIsNotFound() {
        User user = UserUtils.newUserList().getFirst();

        Long randomId = 999L;
        SaleProductBySalePostRequest invalidSaleProductBySalePostRequest = SaleProductUtils.newSaleProductBySalePostRequest().withProductId(randomId);

        SalePostRequest postRequest = new SalePostRequest(Collections.singletonList(invalidSaleProductBySalePostRequest));

        BDDMockito.when(productService.findByIdOrThrowsNotFoundException(randomId)).thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> service.create(user, postRequest))
                .isInstanceOf(NotFoundException.class);
    }

}