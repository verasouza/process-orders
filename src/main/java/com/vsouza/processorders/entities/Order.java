package com.vsouza.processorders.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;
    private LocalDate date;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public boolean hasProduct(Product product) {
        return orderProducts.stream()
                .anyMatch(op -> op.getProduct().getId().equals(product.getId()));
    }

    public void addProduct(Product product) {
        if (hasProduct(product)) {
            return;
        }
        OrderProduct orderProduct = new OrderProduct(this, product);
        orderProducts.add(orderProduct);
        this.total = this.total.add(product.getProductPrice());
        product.addOrderProduct(orderProduct);
    }


}
