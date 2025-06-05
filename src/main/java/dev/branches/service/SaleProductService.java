package dev.branches.service;

import dev.branches.model.SaleProduct;
import dev.branches.repository.SaleProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleProductService {
    private final SaleProductRepository repository;

    public List<SaleProduct> findAllBySaleId(Long saleId) {
        return repository.findAllBySale_Id(saleId);
    }

    public List<SaleProduct> saveAll(List<SaleProduct> saleProductList) {
        return repository.saveAll(saleProductList);
    }
}
