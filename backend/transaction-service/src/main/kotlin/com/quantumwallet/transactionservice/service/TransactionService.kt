package com.quantumwallet.transactionservice.service

import com.quantumwallet.transactionservice.model.Transaction
import com.quantumwallet.transactionservice.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository
) {

    @Transactional
    fun createTransaction(transaction: Transaction): Transaction {
        // O valor deve ser estritamente maior que zero
        if (transaction.amount <= BigDecimal.ZERO) {
            throw IllegalArgumentException("Transaction amount must be greater than zero.")
        }

        return transactionRepository.save(transaction)
    }
}