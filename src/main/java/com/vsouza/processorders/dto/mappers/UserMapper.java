package com.vsouza.processorders.dto.mappers;

import com.vsouza.processorders.dto.entities.User;
import com.vsouza.processorders.dto.model.UserOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "userId", source = "user.id")
	UserOrderResponse userToUserOrderResponse(User user);
}
