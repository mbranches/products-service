package dev.branches.repository;

import dev.branches.model.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleProductRepository extends JpaRepository<SaleProduct, Long> {
    List<SaleProduct> findAllBySale_Id(Long saleId);
}
