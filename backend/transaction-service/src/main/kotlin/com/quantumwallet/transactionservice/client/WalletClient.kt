package com.quantumwallet.transactionservice.client

import com.quantumwallet.transactionservice.dto.WalletResponseDTO
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.UUID

@Component
class WalletClient(private val walletWebClient: WebClient) {

    // Alterado para buscar pelo ID da própria carteira
    fun getWalletById(walletId: UUID): WalletResponseDTO? {
        return try {
            walletWebClient.get()
                .uri("/api/wallets/$walletId") // 🆕 Nova rota direta da carteira
                .retrieve()
                .bodyToMono(WalletResponseDTO::class.java)
                .block()
        } catch (e: Exception) {
            println("Error communicating with wallet-service: ${e.message}")
            null
        }
    }
}