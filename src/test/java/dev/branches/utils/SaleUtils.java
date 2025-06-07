package dev.branches.utils;

import dev.branches.model.Sale;
import dev.branches.model.User;

import java.util.ArrayList;
import java.util.List;

public class SaleUtils {
    public static List<Sale> newSaleList() {
        User user = UserUtils.newUserList().getFirst();

        Sale sale1 = Sale.builder().id(1L).user(user).totalValue(34565D).build();
        Sale sale2 = Sale.builder().id(2L).user(user).totalValue(674D).build();
        Sale sale3 = Sale.builder().id(3L).user(user).totalValue(65000D).build();

        return new ArrayList<>(List.of(sale1, sale2, sale3));
    }
}
