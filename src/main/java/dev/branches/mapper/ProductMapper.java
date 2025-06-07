package dev.branches.mapper;

import dev.branches.dto.request.ProductPostRequest;
import dev.branches.dto.response.ProductGetResponse;
import dev.branches.dto.response.ProductPostResponse;
import dev.branches.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    List<ProductGetResponse> toProductGetResponseList(List<Product> productList);

    ProductPostResponse toProductPostResponse(Product product);

    @Mapping(target = "id", ignore = true)
    Product toProduct(ProductPostRequest postRequest);
}
