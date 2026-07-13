package com.quantumwallet.transactionservice.client

import com.quantumwallet.transactionservice.dto.WalletResponseDTO
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.UUID

@Component
class WalletClient(private val walletWebClient: WebClient) {

    fun getWalletByUserId(userId: UUID): WalletResponseDTO? {
        return try {
            walletWebClient.get()
                .uri("/api/wallets/user/$userId")
                .retrieve()
                // Faz a chamada síncrona (.block()) porque a validação de saldo bloqueia a transação
                .bodyToMono(WalletResponseDTO::class.java)
                .block()
        } catch (e: Exception) {
            // Em produção trataríamos erros específicos, aqui vamos logar e retornar null para segurança
            println("Error communicating with wallet-service: ${e.message}")
            null
        }
    }
}