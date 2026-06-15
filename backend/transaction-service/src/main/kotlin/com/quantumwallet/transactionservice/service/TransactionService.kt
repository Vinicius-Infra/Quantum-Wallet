package com.quantumwallet.transactionservice.service

import com.quantumwallet.transactionservice.model.Transaction
import com.quantumwallet.transactionservice.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository
) {

    @Transactional
    fun createTransaction(transaction: Transaction): Transaction {
        
        return transactionRepository.save(transaction)
    }
}