package dev.branches.dto.response;

import dev.branches.model.Sale;

public record SaleGetResponse(Long id, UserBySaleGetResponse customer, Double totalValue) {
    public static SaleGetResponse of(Sale sale) {
        UserBySaleGetResponse user = UserBySaleGetResponse.of(sale.getUser());


        return new SaleGetResponse(sale.getId(), user, sale.getTotalValue());
    }
}
