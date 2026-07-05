package com.quantumwallet.transactionservice.service

import com.quantumwallet.transactionservice.model.Transaction
import com.quantumwallet.transactionservice.model.TransactionType
import com.quantumwallet.transactionservice.repository.TransactionRepository
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.math.BigDecimal
import java.util.UUID

class TransactionServiceTest {

    private val repository = mock(TransactionRepository::class.java)
    private val service = TransactionService(repository)

    @Test
    fun `should create transaction when balance is sufficient`() {
        val walletId = UUID.randomUUID()
        val existingTx = Transaction(id = UUID.randomUUID(), amount = BigDecimal("100.00"), type = TransactionType.DEPOSIT, destinationWalletId = walletId, sourceWalletId = UUID.randomUUID())
        val newTx = Transaction(id = UUID.randomUUID(), amount = BigDecimal("40.00"), type = TransactionType.WITHDRAW, sourceWalletId = walletId, destinationWalletId = UUID.randomUUID())

        `when`(repository.findBySourceWalletIdOrDestinationWalletId(walletId, walletId)).thenReturn(listOf(existingTx))
        `when`(repository.save(newTx)).thenReturn(newTx)

        val result = service.createTransaction(newTx)
        assertNotNull(result)
    }

    @Test
    fun `should throw exception when balance is insufficient`() {
        val walletId = UUID.randomUUID()
        val newTx = Transaction(id = UUID.randomUUID(), amount = BigDecimal("50.00"), type = TransactionType.WITHDRAW, sourceWalletId = walletId, destinationWalletId = UUID.randomUUID())

        `when`(repository.findBySourceWalletIdOrDestinationWalletId(walletId, walletId)).thenReturn(emptyList())

        assertThrows(IllegalStateException::class.java) {
            service.createTransaction(newTx)
        }
    }
}