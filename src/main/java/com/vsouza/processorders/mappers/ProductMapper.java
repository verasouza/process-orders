package com.vsouza.processorders.mappers;

import com.vsouza.processorders.entities.Product;
import com.vsouza.processorders.dto.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	@Mapping(target = "productPrice", source = "productResponse.value")
	@Mapping(target = "id", source = "productResponse.productId")
	Product toProduct(ProductResponse productResponse);

	@Mapping(target = "value", source = "product.productPrice")
	@Mapping(target = "productId", source = "product.id")
	ProductResponse toProductResponse(Product product);
}
