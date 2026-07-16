package com.quantumwallet.transactionservice.client

import com.quantumwallet.transactionservice.dto.WalletResponseDTO
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.UUID

@Component
class WalletClient(private val walletWebClient: WebClient) {

    fun getWalletById(walletId: UUID): WalletResponseDTO? {
        return try {
            walletWebClient.get()
                .uri("/api/wallets/$walletId")
                .retrieve()
                .bodyToMono(WalletResponseDTO::class.java)
                .block()
        } catch (e: Exception) {
            println("Error communicating with wallet-service: ${e.message}")
            null
        }
    }
}