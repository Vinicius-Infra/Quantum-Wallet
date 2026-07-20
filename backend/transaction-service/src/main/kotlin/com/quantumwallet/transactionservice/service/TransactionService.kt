package com.quantumwallet.transactionservice.service

import com.quantumwallet.transactionservice.client.WalletClient
import com.quantumwallet.transactionservice.model.Transaction
import com.quantumwallet.transactionservice.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.UUID

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val walletClient: WalletClient
) {

    @Transactional
    fun createTransaction(transaction: Transaction): Transaction {
        // Validação adicional de segurança para o valor
        if (transaction.amount <= BigDecimal.ZERO) {
            throw IllegalArgumentException("Transaction amount must be greater than zero.")
        }

        // Busca os dados da carteira origem no wallet-service via WebClient
        val wallet = walletClient.getWalletById(transaction.sourceWalletId)
            ?: throw IllegalStateException("Source wallet not found or wallet-service is offline.")

        // Valida se há saldo suficiente para a transação
        if (wallet.balance < transaction.amount) {
            throw IllegalArgumentException("Insufficient funds. Available balance: ${wallet.balance}")
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
}