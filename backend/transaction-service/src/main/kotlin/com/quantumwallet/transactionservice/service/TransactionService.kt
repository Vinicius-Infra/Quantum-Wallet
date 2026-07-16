package com.quantumwallet.transactionservice.service

import com.quantumwallet.transactionservice.client.WalletClient
import com.quantumwallet.transactionservice.model.Transaction
import com.quantumwallet.transactionservice.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val walletClient: WalletClient
) {

    @Transactional
    fun createTransaction(transaction: Transaction): Transaction {
        
        val wallet = walletClient.getWalletById(transaction.sourceWalletId)
            ?: throw IllegalStateException("Source wallet not found or wallet-service is offline.")

        
        if (wallet.balance < transaction.amount) {
            throw IllegalArgumentException("Insufficient funds. Available balance: ${wallet.balance}")
        }

        
        return transactionRepository.save(transaction)
    }
}