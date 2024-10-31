package com.vsouza.processorders.service;

import com.vsouza.processorders.dto.entities.Order;
import com.vsouza.processorders.dto.entities.OrderProduct;
import com.vsouza.processorders.dto.entities.Product;
import com.vsouza.processorders.dto.entities.User;
import com.vsouza.processorders.dto.mappers.OrderMapper;
import com.vsouza.processorders.dto.model.*;
import com.vsouza.processorders.repositories.IOrderRepository;
import com.vsouza.processorders.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {

    final UserService userService;
    final OrderRepository orderRepository;
    final ProductService productService;
    final OrderMapper orderMapper;
	final IOrderRepository iOrderRepository;

    public List<UserOrderResponse> getUserOrders(FilterOrdersRequest filterOrdersRequest) {
        return iOrderRepository.filterOrders(filterOrdersRequest);
    }

    protected Order processOrder(FileRequest orderData, User user) {
        return orderRepository.findById(orderData.getOrderId())
                .orElseGet(() -> {
                    Order newOrder = Order.builder()
                            .id(orderData.getOrderId())
                            .date(orderData.getDate())
                            .orderProducts(new ArrayList<>())
                            .user(user)
                            .build();
                    return orderRepository.save(newOrder);
                });
    }

    private void processOrderProduct(Order order, Product product) {
        boolean orderProductExists = order.getOrderProducts().stream()
                .anyMatch(op -> op.getProduct().getId().equals(product.getId()));

        if (!orderProductExists) {
            OrderProduct orderProduct = new OrderProduct(order, product);

            order.getOrderProducts().add(orderProduct);
            product.getOrderProducts().add(orderProduct);

            orderRepository.save(order);
        }
    }

    protected void saveOrder(FileRequest orderRequest) {
        User user = userService.processUser(orderRequest);
        Order order = processOrder(orderRequest, user);
        Product product = productService.processProduct(orderRequest);
        processOrderProduct(order, product);
    }

    @Transactional
    public void processOrders(List<FileRequest> ordersRequests) {
        for (FileRequest orderData : ordersRequests) {
            try {
                saveOrder(orderData);
            } catch (Exception e) {
                log.error("Erro ao salvar pedido: {}", orderData, e);
            }
        }
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
