package com.example.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderResponseDto {

    private Integer orderId;
    private String productId;
    private Integer userId;
    private Double amount;
    private OrderStatus status;

}
