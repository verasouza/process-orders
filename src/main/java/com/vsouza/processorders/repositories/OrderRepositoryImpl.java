package com.vsouza.processorders.repositories;

import com.vsouza.processorders.dto.entities.Order;
import com.vsouza.processorders.dto.entities.OrderProduct;
import com.vsouza.processorders.dto.entities.Product;
import com.vsouza.processorders.dto.entities.User;
import com.vsouza.processorders.dto.model.FilterOrdersRequest;
import com.vsouza.processorders.dto.model.OrderResponse;
import com.vsouza.processorders.dto.model.ProductResponse;
import com.vsouza.processorders.dto.model.UserOrderResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderRepositoryImpl implements IOrderRepository {


	final EntityManager entityManager;
	final int totalPerPage = 15;


	@Override
	public List<UserOrderResponse> filterOrders(FilterOrdersRequest filterOrdersRequest) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);

		Root<User> userRoot = cq.from(User.class);
		Join<User, Order> orderJoin = userRoot.join("orders");
		Join<Order, OrderProduct> orderProductJoin = orderJoin.join("orderProducts");
		Join<OrderProduct, Product> productJoin = orderProductJoin.join("product");

		Predicate[] predicates = filter(filterOrdersRequest, cb, userRoot);

		cq.multiselect(
				userRoot.get("id"),
				userRoot.get("name"),
				orderJoin.get("id"),
				orderJoin.get("date"),
				orderJoin.get("total"),
				productJoin.get("id"),
				productJoin.get("productPrice")
		);

		cq.where(predicates);

		TypedQuery<Tuple> query = entityManager.createQuery(cq);
		query.setFirstResult((filterOrdersRequest.getPage() - 1) * totalPerPage);
		query.setMaxResults(totalPerPage);
		List<Tuple> results = query.getResultList();

		return getUserOrders(results);
	}

	private Predicate[] filter(FilterOrdersRequest filterOrdersRequest, CriteriaBuilder criteriaBuilder, Root<User> root) {
		List<Predicate> predicates = new ArrayList<>();
		Path<Order> orderPath = root.get("orders");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		if (filterOrdersRequest.getOrderId() != null) {
			predicates.add(criteriaBuilder.in(orderPath.get("id")).value(filterOrdersRequest.getOrderId()));
		}

		if(StringUtils.isNotEmpty(filterOrdersRequest.getStartDate())) {
			LocalDate startDate = LocalDate.parse(filterOrdersRequest.getStartDate(), formatter);
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(orderPath.get("date"), startDate));
		}

		if(StringUtils.isNotEmpty(filterOrdersRequest.getEndDate())) {
			LocalDate endDate = LocalDate.parse(filterOrdersRequest.getStartDate(), formatter);
			predicates.add(criteriaBuilder.lessThan(orderPath.get("date"), endDate));
		}

		if(StringUtils.isNotEmpty(filterOrdersRequest.getUsername())){
			predicates.add(criteriaBuilder.like(root.get("name"), "%"+filterOrdersRequest.getUsername()+"%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);

	}

	public List<UserOrderResponse> getUserOrders(List<Tuple> tuples){
		List<UserOrderResponse> userOrderResponses = new ArrayList<>();
		Map<Long, UserOrderResponse> userResponseMap = new HashMap<>();

		for (Tuple tuple : tuples) {
			Long userId = tuple.get(0, Long.class);
			String userName = tuple.get(1, String.class);
			Long orderId = tuple.get(2, Long.class);
			LocalDate orderDate = tuple.get(3, LocalDate.class);
			BigDecimal orderTotal = tuple.get(4, BigDecimal.class);
			Long productId = tuple.get(5, Long.class);
			BigDecimal productPrice = tuple.get(6, BigDecimal.class);

			UserOrderResponse userResponse = userResponseMap.get(userId);
			if (userResponse == null) {
				userResponse = new UserOrderResponse();
				userResponse.setUserId(userId.toString());
				userResponse.setName(userName);
				userResponseMap.put(userId, userResponse);
				userOrderResponses.add(userResponse);
			}

			OrderResponse orderResponse = new OrderResponse();
			orderResponse.setOrderId(orderId.toString());
			orderResponse.setDate(String.valueOf(orderDate));
			orderResponse.setTotal(orderTotal.toString());

			ProductResponse productResponse = new ProductResponse();
			productResponse.setProductId(productId.toString());
			productResponse.setValue(productPrice.toString());

			orderResponse.getProducts().add(productResponse);
			userResponse.getOrders().add(orderResponse);
		}
		return userOrderResponses;
	}

}
