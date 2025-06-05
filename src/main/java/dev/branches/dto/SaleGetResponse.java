package dev.branches.dto;

import dev.branches.model.Sale;
import dev.branches.model.SaleProduct;

import java.util.List;

public record SaleGetResponse(Long id, UserBySaleGetResponse user, Double totalValue) {
    public static SaleGetResponse of(Sale sale) {
        UserBySaleGetResponse user = UserBySaleGetResponse.of(sale.getUser());


        return new SaleGetResponse(sale.getId(), user, sale.getTotalValue());
    }
}
