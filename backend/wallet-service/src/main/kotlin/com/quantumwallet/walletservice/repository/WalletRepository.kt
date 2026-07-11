package com.quantumwallet.walletservice.repository

import com.quantumwallet.walletservice.model.Wallet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID
import java.util.Optional

@Repository
interface WalletRepository : JpaRepository<Wallet, UUID> {
    // Busca a carteira de um usuário específico para validações futuras
    fun findByUserId(userId: UUID): Optional<Wallet>
}