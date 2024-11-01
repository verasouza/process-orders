package com.vsouza.processorders.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class OrderFileRequest {

    @NotNull(message = "Obrigatorio informar o Id do usuário")
    private Long userId;
    @NotEmpty(message = "Obrigatorio informar nome do usuário")
    private String userName;
    @NotNull(message = "Obrigatorio informar o Id do pedido")
    private Long orderId;
    @NotNull(message = "Obrigatorio informar o Id do produto")
    private Long productId;
    @NotNull(message = "Obrigatorio informar o preço do produto")
    private BigDecimal value;
    @NotNull(message = "Obrigatorio informar data do pedido")
    private LocalDate date;
}
