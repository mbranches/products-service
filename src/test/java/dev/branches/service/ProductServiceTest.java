package dev.branches.service;

import dev.branches.dto.request.ProductPostRequest;
import dev.branches.dto.response.ProductGetResponse;
import dev.branches.dto.response.ProductPostResponse;
import dev.branches.exception.NotFoundException;
import dev.branches.mapper.ProductMapper;
import dev.branches.model.Product;
import dev.branches.repository.ProductRepository;
import dev.branches.utils.ProductUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService service;
    @Mock
    private ProductRepository repository;
    @Mock
    private ProductMapper mapper;
    private List<Product> productList;
    private List<ProductGetResponse> productGetResponseList;

    @BeforeEach
    void init() {
        productList = ProductUtils.newProductList();
        productGetResponseList = ProductUtils.newProductGetResponseList();
    }

    @Test
    @DisplayName("findAll returns all product when successful")
    @Order(1)
    void findAll_ReturnsAllProduct_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(productList);
        BDDMockito.when(mapper.toProductGetResponseList(productList)).thenReturn(productGetResponseList);

        List<ProductGetResponse> response = service.findAll();

        assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(productGetResponseList);
    }

    @Test
    @DisplayName("findByIdOrThrowsNotFoundException returns found product when successful")
    @Order(2)
    void findByIdOrThrowsNotFoundException_ReturnsFoundProduct_WhenSuccessful() {
        Product productToBeFound = productList.getFirst();
        Long idToSearch = productToBeFound.getId();

        BDDMockito.when(repository.findById(idToSearch)).thenReturn(Optional.of(productToBeFound));

        Product response = service.findByIdOrThrowsNotFoundException(idToSearch);

        assertThat(response)
                .isNotNull()
                .isEqualTo(productToBeFound);
    }

    @Test
    @DisplayName("findByIdOrThrowsNotFoundException throws NotFoundException when the given id is not found")
    @Order(3)
    void findByIdOrThrowsNotFoundException_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() {
        Product productToBeFound = productList.getFirst();
        Long idToSearch = productToBeFound.getId();

        BDDMockito.when(repository.findById(idToSearch)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findByIdOrThrowsNotFoundException(idToSearch))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Product with id '%s' not found".formatted(idToSearch));
    }

    @Test
    @DisplayName("create creates product when successful")
    @Order(4)
    void create_CreatesProduct_WhenSuccessful() {
        ProductPostRequest postRequest = ProductUtils.newProductPostRequest();
        ProductPostResponse postResponse = ProductUtils.newProductPostResponse();

        Product productToSave = ProductUtils.newProductToSave();
        Product savedProduct = ProductUtils.newProductSaved();

        BDDMockito.when(mapper.toProduct(postRequest)).thenReturn(productToSave);
        BDDMockito.when(repository.save(productToSave)).thenReturn(savedProduct);
        BDDMockito.when(mapper.toProductPostResponse(savedProduct)).thenReturn(postResponse);

        ProductPostResponse response = service.create(postRequest);

        assertThat(response)
                .isNotNull()
                .isEqualTo(postResponse);
    }
}