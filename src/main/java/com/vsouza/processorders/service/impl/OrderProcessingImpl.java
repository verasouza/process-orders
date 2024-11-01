package com.vsouza.processorders.service.impl;

import com.vsouza.processorders.dto.request.OrderFileRequest;
import com.vsouza.processorders.entities.Order;
import com.vsouza.processorders.entities.OrderProduct;
import com.vsouza.processorders.entities.Product;
import com.vsouza.processorders.entities.User;
import com.vsouza.processorders.repositories.OrderRepository;
import com.vsouza.processorders.service.OrderProcessing;
import com.vsouza.processorders.service.ProductService;
import com.vsouza.processorders.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderProcessingImpl implements OrderProcessing {

	final UserService userService;
	final OrderRepository orderRepository;
	final ProductService productService;


	@Override
	public void processOrder(OrderFileRequest orderFileRequest) {
		User user = userService.processUser(orderFileRequest);
		Order order = getOrCreateOrder(orderFileRequest, user);
		Product product = productService.processProduct(orderFileRequest);

		processOrderProduct(order, product);
	}

	@Override
	public void processMultipleOrders(List<OrderFileRequest> orderFileRequests) {
		orderFileRequests.forEach(this::processOrder);
	}

	//procura ou cria um novo pedido
	private Order getOrCreateOrder(OrderFileRequest orderRequest, User user) {
		return orderRepository.findById(orderRequest.getOrderId())
				.orElseGet(() -> createNewOrder(orderRequest, user));
	}

	//cria novo pedido a partir da request de arquivo
	private Order createNewOrder(OrderFileRequest orderRequest, User user) {
		Order newOrder = Order.builder()
				.id(orderRequest.getOrderId())
				.date(orderRequest.getDate())
				.orderProducts(new ArrayList<>())
				.total(BigDecimal.ZERO)
				.user(user)
				.build();
		return orderRepository.save(newOrder);
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
}
