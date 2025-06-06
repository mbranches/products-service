package dev.branches.dto.response;

import dev.branches.model.Sale;
import dev.branches.model.SaleProduct;

import java.util.List;

public record SalePostResponse(Long id, UserBySalePostResponse customer, List<SaleProductBySalePostResponse> products, Double totalValue) {

    public static SalePostResponse of(Sale sale, List<SaleProduct> saleProductList) {
        UserBySalePostResponse user = UserBySalePostResponse.of(sale.getUser());

        List<SaleProductBySalePostResponse> products = saleProductList.stream().map(SaleProductBySalePostResponse::of).toList();

        return new SalePostResponse(sale.getId(), user, products, sale.getTotalValue());
    }
}
