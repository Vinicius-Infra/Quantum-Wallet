package com.quantumwallet.walletservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun transactionWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://localhost:8081") // Aponta para o Transaction-Service
            .build()
    }
}