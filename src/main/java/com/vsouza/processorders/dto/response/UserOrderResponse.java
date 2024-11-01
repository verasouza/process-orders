package com.vsouza.processorders.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserOrderResponse {

	private String userId;
	private String name;
	private List<OrderResponse> orders = new ArrayList<>();;
}
