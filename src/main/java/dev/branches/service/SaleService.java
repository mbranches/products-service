package dev.branches.service;

import dev.branches.dto.SaleGetResponse;
import dev.branches.dto.SalePostRequest;
import dev.branches.dto.SalePostResponse;
import dev.branches.model.Product;
import dev.branches.model.Sale;
import dev.branches.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository repository;
    private final ProductService productService;

    public List<SaleGetResponse> findAll() {
        List<Sale> sales = repository.findAll();

        return sales.stream()
                .map(SaleGetResponse::of)
                .toList();
    }

    public SalePostResponse create(SalePostRequest postRequest) {
        Product product = productService.findByIdOrThrowsNotFoundException(postRequest.productId());

        Integer quantity = postRequest.quantity();

        Sale saleToSave = Sale.builder()
                .product(product)
                .quantity(quantity)
                .totalValue(product.getUnitPrice() * quantity)
                .build();

        Sale savedSale = repository.save(saleToSave);

        return SalePostResponse.of(savedSale);
    }
}
