package com.vsouza.processorders.service;

import com.vsouza.processorders.dto.entities.User;
import com.vsouza.processorders.dto.mappers.UserMapper;
import com.vsouza.processorders.dto.model.FileRequest;
import com.vsouza.processorders.dto.model.UserOrderResponse;
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

	public User processUser(FileRequest orderData) {
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
