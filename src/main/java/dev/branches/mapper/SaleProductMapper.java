package dev.branches.mapper;

import dev.branches.dto.response.SaleProductBySaleDetailsGetResponse;
import dev.branches.dto.response.SaleProductBySalePostResponse;
import dev.branches.model.SaleProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SaleProductMapper {
    @Mapping(target = "name", source = "saleProduct.product.name")
    @Mapping(target = "unitPrice", source = "saleProduct.product.unitPrice")
    SaleProductBySaleDetailsGetResponse toSaleProductBySaleDetailsGetResponse(SaleProduct saleProduct);

    @Mapping(target = "name", source = "saleProduct.product.name")
    @Mapping(target = "unitPrice", source = "saleProduct.product.unitPrice")
    SaleProductBySalePostResponse toSaleProductBySalePostResponse(SaleProduct saleProduct);
}
