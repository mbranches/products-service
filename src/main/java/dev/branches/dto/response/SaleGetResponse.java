package dev.branches.dto.response;

import dev.branches.model.Sale;

import java.util.List;

public record SaleGetResponse(Long id, UserBySaleGetResponse customer, Double totalValue) {
    public static SaleGetResponse of(Sale sale) {
        UserBySaleGetResponse user = UserBySaleGetResponse.of(sale.getUser());


        return new SaleGetResponse(sale.getId(), user, sale.getTotalValue());
    }

    public static List<SaleGetResponse> saleGetResponseListOf(List<Sale> saleList) {
        return saleList.stream()
                .map(SaleGetResponse::of)
                .toList();
    }
}
