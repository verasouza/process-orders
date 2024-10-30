package com.vsouza.processorders.dto.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private Long productId;
    private Double value;
}
