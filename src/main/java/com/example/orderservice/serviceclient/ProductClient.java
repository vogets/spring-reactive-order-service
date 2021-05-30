package com.example.orderservice.serviceclient;

import com.example.orderservice.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {

    private WebClient webClient;

    @Autowired
    private ReactiveCircuitBreakerFactory cbFactory;

    public ProductClient(@Value("${product.service.url}") String url)
    {
        this.webClient=WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<ProductDTO> getProductById(final String productId)
    {
        return this.webClient.get()
                .uri("{id}",productId)
                .retrieve()
                .bodyToMono(ProductDTO.class);
    }
}
