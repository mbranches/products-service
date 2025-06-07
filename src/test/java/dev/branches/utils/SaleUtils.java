package dev.branches.utils;

import dev.branches.dto.request.SalePostRequest;
import dev.branches.dto.request.SaleProductBySalePostRequest;
import dev.branches.dto.response.*;
import dev.branches.model.Product;
import dev.branches.model.Sale;
import dev.branches.model.SaleProduct;
import dev.branches.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaleUtils {
    public static List<Sale> newSaleList() {
        User user = UserUtils.newUserList().getFirst();

        Sale sale1 = Sale.builder().id(1L).user(user).totalValue(34565D).build();
        Sale sale2 = Sale.builder().id(2L).user(user).totalValue(674D).build();
        Sale sale3 = Sale.builder().id(3L).user(user).totalValue(65000D).build();

        return new ArrayList<>(List.of(sale1, sale2, sale3));
    }

    public static List<SaleGetResponse> newSaleGetResponseList() {
        UserBySaleGetResponse customer = UserUtils.newUserBySaleGetResponse();

        return newSaleList().stream().map(sale ->
            new SaleGetResponse(sale.getId(), customer, sale.getTotalValue())
        ).toList();
    }

    public static SaleBySaleDetailsGetResponse newSaleBySaleDetailsGetResponse() {
        Sale sale = newSaleList().getFirst();

        User user = sale.getUser();
        UserBySaleGetResponse userBySaleGetResponse = new UserBySaleGetResponse(user.getId(), user.getFirstName(), user.getLastName());

        SaleProduct saleProduct = SaleProductUtils.newSaleProductExisting();
        Product product = saleProduct.getProduct();
        SaleProductBySaleDetailsGetResponse saleProductBySaleDetailsGetResponse = new SaleProductBySaleDetailsGetResponse(saleProduct.getId(), product.getName(), product.getUnitPrice(), saleProduct.getQuantity(), sale.getTotalValue());

        return new SaleBySaleDetailsGetResponse(
                sale.getId(),
                userBySaleGetResponse,
                Collections.singletonList(saleProductBySaleDetailsGetResponse),
                sale.getTotalValue()
        );
    }

    public static Sale newSaleToSave() {
        User user = UserUtils.newUserList().getFirst();

        return Sale.builder().user(user).totalValue(34565D).build();
    }

    public static Sale newSaleSaved() {
        return newSaleToSave().withId(4L);
    }

    public static SalePostRequest newSalePostRequest() {
        SaleProductBySalePostRequest saleProductBySalePostRequest = SaleProductUtils.newSaleProductBySalePostRequest();

        return new SalePostRequest(Collections.singletonList(saleProductBySalePostRequest));
    }

    public static SalePostResponse newSalePostResponse() {
        Sale savedSale = newSaleSaved();

        UserBySalePostResponse customer = UserUtils.newUserBySalePostResponse();

        List<SaleProductBySalePostResponse> products = Collections.singletonList(SaleProductUtils.newSaleProductBySalePostResponse());

        return new SalePostResponse(savedSale.getId(), customer, products, savedSale.getTotalValue());
    }
}
