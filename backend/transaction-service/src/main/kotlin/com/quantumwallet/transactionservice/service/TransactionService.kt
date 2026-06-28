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
        return transactionRepository.save(transaction)
    }

    // Certifique-se de que este método está exatamente assim para a Controller achar!
    @Transactional(readOnly = true)
    fun getTransactionsByWallet(walletId: String): List<Transaction> {
        val uuid = UUID.fromString(walletId)
        return transactionRepository.findBySourceWalletIdOrDestinationWalletId(uuid, uuid)
    }

    // Caso sua Controller precise listar tudo se o id for nulo, mantemos esse de apoio
    @Transactional(readOnly = true)
    fun getAllTransactions(): List<Transaction> {
        return transactionRepository.findAll()
    }
}