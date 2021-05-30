package com.example.orderservice.serviceclient;

import com.example.orderservice.dto.TransactionRequestDto;
import com.example.orderservice.dto.TransactionResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserClient {
    private WebClient webClient;
    public UserClient(@Value("${user.service.url}") String url)
    {
        this.webClient=WebClient.builder().baseUrl(url).build();
    }

    public Mono<TransactionResponseDto> authorizeTransaction(final TransactionRequestDto requestDto)
    {
        return this.webClient.post()
                .uri("transaction")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(TransactionResponseDto.class) ;
    }
}
