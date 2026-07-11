package com.quantumwallet.walletservice.service

import com.quantumwallet.walletservice.model.Wallet
import com.quantumwallet.walletservice.repository.WalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class WalletService(private val walletRepository: WalletRepository) {

    @Transactional
    fun createWallet(wallet: Wallet): Wallet {
        // Regra de Ouro: Valida se o usuário já possui uma carteira ativa
        val existingWallet = walletRepository.findByUserId(wallet.userId)
        if (existingWallet.isPresent) {
            throw IllegalArgumentException("User already has an active wallet.")
        }
        return walletRepository.save(wallet)
    }

    fun getWalletByUserId(userId: UUID): Wallet {
        return walletRepository.findByUserId(userId)
            .orElseThrow { NoSuchElementException("Wallet not found for user: $userId") }
    }
}