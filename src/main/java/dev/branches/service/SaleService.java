package dev.branches.service;

import dev.branches.dto.request.SalePostRequest;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository repository;
    private final SaleProductService saleProductService;
    private final ProductService productService;
    private final SaleMapper mapper;

    public List<SaleGetResponse> findAll() {
        List<Sale> sales = repository.findAll();

        return mapper.toSaleGetResponseList(sales);
    }

    public List<SaleGetResponse> findAllByUser(User user) {
        List<Sale> sales = repository.findAllByUser(user);

        return mapper.toSaleGetResponseList(sales);
    }

    public SaleBySaleDetailsGetResponse findSaleDetailsById(Long id) {
        Sale sale = findByIdOrThrowsNotFoundException(id);

        List<SaleProduct> products = saleProductService.findAllBySaleId(sale.getId());

        return mapper.toSaleBySaleDetailsGetResponse(sale, products);
    }

    public SaleBySaleDetailsGetResponse findMySaleDetailsById(User user, Long id) {
        Sale sale = findByIdOrThrowsNotFoundException(id);

        if (!sale.getUser().equals(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        List<SaleProduct> products = saleProductService.findAllBySaleId(sale.getId());

        return mapper.toSaleBySaleDetailsGetResponse(sale, products);
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

        return mapper.toSalePostResponse(savedSale, savedSaleProducts);
    }
}
