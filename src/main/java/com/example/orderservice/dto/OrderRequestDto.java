package com.example.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderRequestDto {

    private Integer userId;
    private String productId;

}
