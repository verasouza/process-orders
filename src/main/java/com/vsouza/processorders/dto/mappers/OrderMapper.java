package com.vsouza.processorders.dto.mappers;

import com.vsouza.processorders.dto.entities.Order;
import com.vsouza.processorders.dto.model.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	@Mapping(target = "orderId", source = "order.id")
	OrderResponse toOrderResponse(Order order);
}
