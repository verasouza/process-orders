package com.vsouza.processorders.controllers;

import com.vsouza.processorders.dto.request.FilterOrdersRequest;
import com.vsouza.processorders.dto.response.OrderResponse;
import com.vsouza.processorders.dto.response.UserOrderResponse;
import com.vsouza.processorders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/filter")
    public ResponseEntity<List<UserOrderResponse>> getOrders(@RequestBody FilterOrdersRequest request) {
        List<UserOrderResponse> orderList = orderService.getUserOrders(request);
        return ResponseEntity.ok(orderList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable long id) {
        OrderResponse orderResponse = orderService.getOrderResponse(id);
        return ResponseEntity.ok(orderResponse);
    }


}
