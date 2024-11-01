package com.vsouza.processorders.mappers;

import com.vsouza.processorders.entities.User;
import com.vsouza.processorders.dto.response.UserOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "userId", source = "user.id")
	UserOrderResponse userToUserOrderResponse(User user);
}
