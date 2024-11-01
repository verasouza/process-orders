package com.vsouza.processorders.repositories;

import com.vsouza.processorders.dto.request.FilterOrdersRequest;
import com.vsouza.processorders.dto.response.UserOrderResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepositoryCustom {

	List<UserOrderResponse> filterOrders(FilterOrdersRequest filterOrdersRequest);
}
