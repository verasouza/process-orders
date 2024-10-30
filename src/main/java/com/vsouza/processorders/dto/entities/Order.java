package com.vsouza.processorders.dto.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Order {

    private Long orderId;
    private Double total;
    private LocalDate date;
    private List<Product> products;

    public void addProduct(Product product) {
        if(products == null) {
            products = new ArrayList<>();
            total = 0.0;
        }
        products.add(product);
        total += product.getValue();
    }

}
