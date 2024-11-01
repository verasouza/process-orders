package com.vsouza.processorders.service;

import com.vsouza.processorders.entities.User;
import com.vsouza.processorders.mappers.UserMapper;
import com.vsouza.processorders.dto.request.OrderFileRequest;
import com.vsouza.processorders.dto.response.UserOrderResponse;
import com.vsouza.processorders.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserService {

	final UserRepository userRepository;
	final UserMapper userMapper;

	public UserOrderResponse getUserResponse(User user){
		return userMapper.userToUserOrderResponse(user);
	}

	public User processUser(OrderFileRequest orderData) {
		return userRepository.findById(orderData.getUserId())
				.orElseGet(() -> {
					User newUser = User.builder()
							.id(orderData.getUserId())
							.name(orderData.getUserName())
							.orders(new ArrayList<>())
							.build();
					return userRepository.save(newUser);
				});
	}


}
