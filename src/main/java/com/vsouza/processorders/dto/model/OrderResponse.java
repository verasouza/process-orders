package com.vsouza.processorders.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

	private String orderId;
	private String date;
	private String total;
	private List<ProductResponse> products = new ArrayList<>();

}
