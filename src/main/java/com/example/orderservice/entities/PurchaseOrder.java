package com.example.orderservice.entities;

import com.example.orderservice.dto.OrderStatus;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@ToString
public class PurchaseOrder {
    @Id
    @GeneratedValue
    private Integer orderId;
    private String productId;
    private Integer userId;
    private Double amount;
    private OrderStatus status;

}
