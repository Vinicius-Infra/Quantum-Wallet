
package com.quantumwallet.transactionservice.repository

import com.quantumwallet.transactionservice.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TransactionRepository : JpaRepository<Transaction, UUID> {
    
    // Uma função inteligente: busca o histórico de transações onde a carteira foi a de origem OU a de destino
    fun findBySourceWalletIdOrDestinationWalletId(sourceWalletId: UUID, destinationWalletId: UUID): List<Transaction>
}