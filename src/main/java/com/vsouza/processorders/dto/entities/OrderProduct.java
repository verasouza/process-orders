package com.vsouza.processorders.dto.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @NoArgsConstructor
@Entity
@Table(name = "order_product")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    public OrderProduct(Order order, Product product) {
        this.order = order;
        this.order.setTotal(new BigDecimal("0.00"));
        this.product = product;
        BigDecimal totalPrice = order.getTotal().add(product.getProductPrice());
        this.order.setTotal(totalPrice);
    }
}
