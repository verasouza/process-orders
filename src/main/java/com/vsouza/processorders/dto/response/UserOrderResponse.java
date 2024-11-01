package com.vsouza.processorders.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class UserOrderResponse {

	private String userId;
	private String name;
	private List<OrderResponse> orders = new ArrayList<>();
}
