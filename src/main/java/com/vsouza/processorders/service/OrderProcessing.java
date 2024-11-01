package com.vsouza.processorders.service;

import com.vsouza.processorders.dto.request.OrderFileRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderProcessing {

	void processOrder(OrderFileRequest orderFileRequest);

	void processMultipleOrders(List<OrderFileRequest> orderFileRequests);
}