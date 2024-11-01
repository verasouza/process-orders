package com.vsouza.processorders.mappers;

import com.vsouza.processorders.entities.Order;
import com.vsouza.processorders.dto.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	@Mapping(target = "orderId", source = "order.id")
	OrderResponse toOrderResponse(Order order);
}
