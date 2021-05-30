package com.example.orderservice.util;

import com.example.orderservice.dto.*;
import com.example.orderservice.entities.PurchaseOrder;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static void setTransactionRequestDto(RequestContext requestContext)
    {
        TransactionRequestDto transactionRequestDto=new TransactionRequestDto();
        transactionRequestDto.setUserId(requestContext.getOrderRequestDto().getUserId());
        transactionRequestDto.setAmount(requestContext.getProductDTO().getPrice());
        requestContext.setTransactionRequestDto(transactionRequestDto);
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext requestContext)
    {
        PurchaseOrder purchaseOrder=new PurchaseOrder();
        purchaseOrder.setUserId(requestContext.getOrderRequestDto().getUserId());
        purchaseOrder.setProductId(requestContext.getOrderRequestDto().getProductId());
        purchaseOrder.setAmount(requestContext.getProductDTO().getPrice());
        TransactionStatusEnum status=requestContext.getTransactionResponseDto().getStatus();
        OrderStatus orderStatus=TransactionStatusEnum.APPROVED.equals(status)? OrderStatus.COMPLETED:OrderStatus.FAILED;
        purchaseOrder.setStatus(orderStatus);
        return purchaseOrder;
    }

    public static OrderResponseDto getOrderResponseDto(PurchaseOrder purchaseOrder)
    {
        OrderResponseDto orderResponseDto=new OrderResponseDto();
        BeanUtils.copyProperties(purchaseOrder,orderResponseDto);
        return orderResponseDto;

    }
}
