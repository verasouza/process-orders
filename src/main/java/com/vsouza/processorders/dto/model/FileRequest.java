package com.vsouza.processorders.dto.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class FileRequest {

    private Long userId;
    private String userName;
    private Long orderId;
    private Long productId;
    private BigDecimal value;
    private LocalDate date;
}
