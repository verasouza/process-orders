package com.vsouza.processorders.service;

import com.vsouza.processorders.dto.request.FilterOrdersRequest;
import com.vsouza.processorders.dto.response.OrderResponse;
import com.vsouza.processorders.dto.response.UserOrderResponse;
import com.vsouza.processorders.entities.Order;
import com.vsouza.processorders.mappers.OrderMapper;
import com.vsouza.processorders.repositories.OrderRepositoryCustom;
import com.vsouza.processorders.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    final OrderRepository orderRepository;
    final OrderMapper orderMapper;
	final OrderRepositoryCustom orderRepositoryCustom;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, OrderRepositoryCustom orderRepositoryCustom) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderRepositoryCustom = orderRepositoryCustom;
    }

    public List<UserOrderResponse> getUserOrders(FilterOrdersRequest filterOrdersRequest) {
        return orderRepositoryCustom.filterOrders(filterOrdersRequest);
    }

    public List<OrderResponse> getOrderResponse(List<Order> orders) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            orderResponses.add(orderMapper.toOrderResponse(order));
        }
        return orderResponses;
    }

    public OrderResponse getOrderResponse(Long orderId) {
        Order order = orderRepository.findById(orderId).isPresent() ? orderRepository.findById(orderId).get() : null;
        return orderMapper.toOrderResponse(order);

    }


}
