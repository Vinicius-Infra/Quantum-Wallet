package com.quantumwallet.transactionservice.service

import com.quantumwallet.transactionservice.model.Transaction
import com.quantumwallet.transactionservice.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.UUID

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository
) {

    @Transactional
    fun createTransaction(transaction: Transaction): Transaction {
        if (transaction.amount <= BigDecimal.ZERO) {
            throw IllegalArgumentException("Transaction amount must be greater than zero.")
        }

        // Validate balance for TRANSFER or WITHDRAW operations
        if (transaction.type.name == "TRANSFER" || transaction.type.name == "WITHDRAW") {
            val sourceId = transaction.sourceWalletId 
                ?: throw IllegalArgumentException("Source wallet ID is required for this transaction type.")
            
            val currentBalance = calculateBalance(sourceId)
            
            if (currentBalance < transaction.amount) {
                throw IllegalStateException("Insufficient balance to complete this transaction.")
            }
        }

        return transactionRepository.save(transaction)
    }

    @Transactional(readOnly = true)
    fun getTransactionsByWallet(walletId: String): List<Transaction> {
        val uuid = UUID.fromString(walletId)
        return transactionRepository.findBySourceWalletIdOrDestinationWalletId(uuid, uuid)
    }

    @Transactional(readOnly = true)
    fun getAllTransactions(): List<Transaction> {
        return transactionRepository.findAll()
    }

    private fun calculateBalance(walletId: UUID): BigDecimal {
        val transactions = transactionRepository.findBySourceWalletIdOrDestinationWalletId(walletId, walletId)
        
        return transactions.fold(BigDecimal.ZERO) { balance, tx ->
            when {
                tx.destinationWalletId == walletId -> balance.add(tx.amount)
                tx.sourceWalletId == walletId -> balance.subtract(tx.amount)
                else -> balance
            }
        }
    }
}