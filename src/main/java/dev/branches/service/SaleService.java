package dev.branches.service;

import dev.branches.dto.*;
import dev.branches.exception.NotFoundException;
import dev.branches.model.Product;
import dev.branches.model.Sale;
import dev.branches.model.SaleProduct;
import dev.branches.model.User;
import dev.branches.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository repository;
    private final SaleProductService saleProductService;
    private final UserService userService;
    private final ProductService productService;

    public List<SaleGetResponse> findAll() {
        List<Sale> sales = repository.findAll();

        return sales.stream()
                .map(SaleGetResponse::of)
                .toList();
    }

    public List<SaleGetResponse> findAllByUser(User user) {
        List<Sale> sales = repository.findAllByUser(user);

        return sales.stream()
                .map(SaleGetResponse::of)
                .toList();
    }

    public SaleBySaleDetailsGetResponse findSaleDetailsById(Long id) {
        Sale sale = findByIdOrThrowsNotFoundException(id);

        List<SaleProduct> products = saleProductService.findAllBySaleId(sale.getId());

        return SaleBySaleDetailsGetResponse.of(sale, products);
    }

    public Sale findByIdOrThrowsNotFoundException(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sale with id '%s' not found".formatted(id)));
    }

    @Transactional
    public SalePostResponse create(User user, SalePostRequest request) {
        List<SaleProduct> saleProductList = request.products()
                .stream()
                .map(saleProduct -> {
                    Integer quantity = saleProduct.quantity();

                    Product product = productService.findByIdOrThrowsNotFoundException(saleProduct.productId());

                    return SaleProduct.builder().product(product).quantity(quantity).totalValue(quantity * product.getUnitPrice()).build();
                }).toList();

        double totalValue = saleProductList.stream().mapToDouble(SaleProduct::getTotalValue).sum();

        Sale saleToSave = Sale.builder()
                .user(user)
                .totalValue(totalValue)
                .build();

        Sale savedSale = repository.save(saleToSave);

        saleProductList.forEach(saleProduct -> saleProduct.setSale(savedSale));

        List<SaleProduct> savedSaleProducts = saleProductService.saveAll(saleProductList);

        return SalePostResponse.of(savedSale, savedSaleProducts);
    }
}
