package com.example.orderservice.service;

import com.example.orderservice.dto.OrderRequestDto;
import com.example.orderservice.dto.OrderResponseDto;
import com.example.orderservice.dto.RequestContext;
import com.example.orderservice.repository.PurchaseOrderRepository;
import com.example.orderservice.serviceclient.ProductClient;
import com.example.orderservice.serviceclient.UserClient;
import com.example.orderservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class OrderFulFilmentService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private UserClient userClient;

    public Mono<OrderResponseDto> processOrder(Mono<OrderRequestDto> orderRequestDtoMono)
    {
        return orderRequestDtoMono.map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
                .map(purchaseOrderRepository::save)
                .map(EntityDtoUtil::getOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());


    }

    private Mono<RequestContext> productRequestResponse(RequestContext context)
    {
        return this.productClient.getProductById(context.getOrderRequestDto().getProductId())
                          .doOnNext(context::setProductDTO)
                          .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)))
                          .thenReturn(context);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext context)
    {
        return this.userClient.authorizeTransaction(context.getTransactionRequestDto())
                .doOnNext(context::setTransactionResponseDto)
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)))
                .thenReturn(context);
    }
}
