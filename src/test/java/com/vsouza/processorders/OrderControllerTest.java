package com.vsouza.processorders;

import com.vsouza.processorders.controllers.OrderController;
import com.vsouza.processorders.dto.request.FilterOrdersRequest;
import com.vsouza.processorders.dto.response.OrderResponse;
import com.vsouza.processorders.dto.response.ProductResponse;
import com.vsouza.processorders.dto.response.UserOrderResponse;
import com.vsouza.processorders.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	OrderService orderService;

	@Test
	void shouldReturnFilteredOrders() throws Exception {


		List<UserOrderResponse> userOrderResponse = List.of(
				UserOrderResponse.builder()
						.userId("2")
						.name("User")
						.orders(List.of(mockOrderResponse()))
						.build()
		);

		when(orderService.getUserOrders(any(FilterOrdersRequest.class))).thenReturn(userOrderResponse);

		mockMvc.perform(post("/orders/filter")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\n" +
								"\t\"orderId\":\"\",\n" +
								"\t\"startDate\":\"2021-07-21\",\n" +
								"\t\"endDate\":\"\",\n" +
								"\t\"username\":\"\",\n" +
								"\t\"page\":1\n" +
								"}")) // JSON do request
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(userOrderResponse.size()));
			}

	ProductResponse mockProductResponse() {
		return ProductResponse
				.builder()
				.productId("1")
				.value("1578.57")
				.build();

	}

	OrderResponse mockOrderResponse() {
		return OrderResponse.builder()
				.orderId("2546454")
				.date("2021-07-21")
				.products(List.of(mockProductResponse()))
				.total("1578.57")
				.build();
	}
}


