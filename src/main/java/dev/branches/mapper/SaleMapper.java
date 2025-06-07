package dev.branches.mapper;

import dev.branches.dto.response.SaleBySaleDetailsGetResponse;
import dev.branches.dto.response.SaleGetResponse;
import dev.branches.dto.response.SalePostResponse;
import dev.branches.model.Sale;
import dev.branches.model.SaleProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = SaleProductMapper.class)
public interface SaleMapper {
    @Mapping(target = "customer", source = "sale.user")
    SaleGetResponse toSaleGetResponse(Sale sale);

    List<SaleGetResponse> toSaleGetResponseList(List<Sale> saleList);

    @Mapping(target = "products", source = "saleProductList")
    @Mapping(target = "customer", source = "sale.user")
    SaleBySaleDetailsGetResponse toSaleBySaleDetailsGetResponse(Sale sale, List<SaleProduct> saleProductList);

    @Mapping(target = "products", source = "saleProductList")
    @Mapping(target = "customer", source = "sale.user")
    SalePostResponse toSalePostResponse(Sale sale, List<SaleProduct> saleProductList);
}
