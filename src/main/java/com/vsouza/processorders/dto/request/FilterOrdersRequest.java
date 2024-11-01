package com.vsouza.processorders.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterOrdersRequest {

	private Long orderId;
	private String startDate;
	private String endDate;
	private String username;
	private int page = 1;
}
