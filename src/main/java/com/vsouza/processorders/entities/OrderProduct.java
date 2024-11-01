package com.vsouza.processorders.entities;

import jakarta.persistence.*;
import lombok.*;

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
        this.product = product;
    }
}
