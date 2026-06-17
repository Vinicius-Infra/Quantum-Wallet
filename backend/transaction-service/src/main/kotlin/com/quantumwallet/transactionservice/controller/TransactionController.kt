package com.quantumwallet.transactionservice.controller

import com.quantumwallet.transactionservice.model.Transaction
import com.quantumwallet.transactionservice.repository.TransactionRepository // Adicione essa importação temporária
import com.quantumwallet.transactionservice.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/transactions")
class TransactionController(
    private val transactionService: TransactionService,
    private val transactionRepository: TransactionRepository // Injetando o repository direto para listar tudo rápido hoje
) {

    @PostMapping
    fun createTransaction(@RequestBody transaction: Transaction): ResponseEntity<Transaction> {
        val createdTransaction = transactionService.createTransaction(transaction)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction)
    }

    // O ENDPOINT QUE VAI SALVAR O SEU TESTE NO NAVEGADOR:
    @GetMapping
    fun getAllTransactions(): ResponseEntity<List<Transaction>> {
        val transactions = transactionRepository.findAll()
        return ResponseEntity.ok(transactions)
    }
}