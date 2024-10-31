package com.vsouza.processorders.repositories;

import com.vsouza.processorders.dto.model.FilterOrdersRequest;
import com.vsouza.processorders.dto.model.UserOrderResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository {

	List<UserOrderResponse> filterOrders(FilterOrdersRequest filterOrdersRequest);
}
