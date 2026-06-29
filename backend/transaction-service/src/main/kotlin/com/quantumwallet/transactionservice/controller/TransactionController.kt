package com.quantumwallet.transactionservice.controller

import com.quantumwallet.transactionservice.model.Transaction
import com.quantumwallet.transactionservice.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/transactions")
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping
    fun createTransaction(@RequestBody transaction: Transaction): ResponseEntity<Transaction> {
        val created = transactionService.createTransaction(transaction)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }

   @GetMapping
    fun getAllTransactions(@RequestParam(required = false) walletId: String?): ResponseEntity<List<Transaction>> {
        val transactions = if (walletId != null) {
            transactionService.getTransactionsByWallet(walletId)
        } else {
            transactionService.getAllTransactions() // Chama o método de listar tudo
        }
        return ResponseEntity.ok(transactions)
    }
}