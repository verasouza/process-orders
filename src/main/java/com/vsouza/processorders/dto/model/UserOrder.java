package com.vsouza.processorders.dto.model;

import com.vsouza.processorders.dto.entities.Order;
import com.vsouza.processorders.dto.entities.User;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserOrder {

    private User user;
    private Set<Order> orders;

    public void addOrder(Order order) {
        if(orders == null) {
            orders = new HashSet<>();
        }
        orders.add(order);
    }
}
