package com.quantumwallet.transactionservice.repository

import com.quantumwallet.transactionservice.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TransactionRepository : JpaRepository<Transaction, UUID> {

    // 🆕 O objetivo de hoje: Buscar transações onde o ID seja a origem OU o destino
    fun findBySourceWalletIdOrDestinationWalletId(sourceWalletId: UUID, destinationWalletId: UUID): List<Transaction>
}