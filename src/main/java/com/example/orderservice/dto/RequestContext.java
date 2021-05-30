package com.example.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RequestContext {
    private OrderRequestDto orderRequestDto;
    private ProductDTO productDTO;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;

    public RequestContext(OrderRequestDto orderRequestDto) {
        this.orderRequestDto = orderRequestDto;
    }
}
