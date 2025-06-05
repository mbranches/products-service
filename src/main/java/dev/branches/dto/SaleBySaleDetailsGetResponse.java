package dev.branches.dto;

import dev.branches.model.Sale;
import dev.branches.model.SaleProduct;

import java.util.List;

public record SaleBySaleDetailsGetResponse(Long id, UserBySaleGetResponse user, List<SaleProductBySaleDetailsGetResponse> products, Double totalValue) {
    public static SaleBySaleDetailsGetResponse of(Sale sale, List<SaleProduct> saleProductList) {
        UserBySaleGetResponse user = UserBySaleGetResponse.of(sale.getUser());

        List<SaleProductBySaleDetailsGetResponse> products = saleProductList.stream().map(SaleProductBySaleDetailsGetResponse::of).toList();

        return new SaleBySaleDetailsGetResponse(sale.getId(), user, products, sale.getTotalValue());
    }
}
