package com.vsouza.processorders.controllers;

import com.vsouza.processorders.dto.model.UserOrder;
import com.vsouza.processorders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/read")
    public ResponseEntity getRecords(){
        orderService.readOrdersFromFile("src/main/resources/data/data_1.txt");
        return ResponseEntity.ok().build();
    }


}
