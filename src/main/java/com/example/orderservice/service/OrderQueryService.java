package com.example.orderservice.service;

import com.example.orderservice.dto.OrderResponseDto;
import com.example.orderservice.entities.PurchaseOrder;
import com.example.orderservice.repository.PurchaseOrderRepository;
import com.example.orderservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class OrderQueryService {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    public Flux<OrderResponseDto> getProductByUserId(int userId)
    {

        return Flux.fromStream(()->purchaseOrderRepository.findByUserId(userId).stream())
        .map(EntityDtoUtil::getOrderResponseDto)
        .subscribeOn(Schedulers.boundedElastic());

    }




}
