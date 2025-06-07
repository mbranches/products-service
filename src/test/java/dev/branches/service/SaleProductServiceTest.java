package dev.branches.service;

import dev.branches.model.Sale;
import dev.branches.model.SaleProduct;
import dev.branches.repository.SaleProductRepository;
import dev.branches.utils.SaleProductUtils;
import dev.branches.utils.SaleUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class SaleProductServiceTest {
    @InjectMocks
    private SaleProductService service;
    @Mock
    private SaleProductRepository repository;

    @Test
    @DisplayName("findAllBySaleId returns found saleProducts when successful")
    @Order(1)
    void findAllBySaleId_ReturnsFoundSaleProducts_WhenSuccessful() {
        Sale sale = SaleUtils.newSaleList().getFirst();
        Long idToSearch = sale.getId();

        SaleProduct saleProductToBeFound = SaleProductUtils.newSaleProductExisting();

        List<SaleProduct> expectedResponse = Collections.singletonList(saleProductToBeFound);

        BDDMockito.when(repository.findAllBySale_Id(idToSearch)).thenReturn(expectedResponse);

        List<SaleProduct> response = service.findAllBySaleId(idToSearch);

        assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @DisplayName("findAllBySaleId returns an empty list when the given sale does not have any saleProduct")
    @Order(2)
    void findAllBySaleId_ReturnsEmptyList_WhenTheGivenSaleDoesNotHaveAnySaleProduct() {
        Sale sale = SaleUtils.newSaleList().getFirst().withTotalValue(0D);
        Long idToSearch = sale.getId();

        BDDMockito.when(repository.findAllBySale_Id(idToSearch)).thenReturn(Collections.emptyList());

        List<SaleProduct> response = service.findAllBySaleId(idToSearch);

        assertThat(response)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("saveAll save the given sale product when successful")
    @Order(3)
    void saveAll_SaveTheGivenSaleProduct_WhenSuccessful() {
        List<SaleProduct> productListToSave = Collections.singletonList(SaleProductUtils.newSaleProductToSave());
        List<SaleProduct> savedProductList = Collections.singletonList(SaleProductUtils.newSaleProductSaved());

        BDDMockito.when(repository.saveAll(productListToSave)).thenReturn(savedProductList);

        List<SaleProduct> response = service.saveAll(productListToSave);

        assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(savedProductList);
    }
}