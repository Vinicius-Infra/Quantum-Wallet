package com.quantumwallet.walletservice.controller

import com.quantumwallet.walletservice.model.Wallet
import com.quantumwallet.walletservice.service.WalletService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/wallets")
class WalletController(private val walletService: WalletService) {

    @PostMapping
    fun createWallet(@RequestBody wallet: Wallet): ResponseEntity<Any> {
        return try {
            val createdWallet = walletService.createWallet(wallet)
            ResponseEntity.status(HttpStatus.CREATED).body(createdWallet)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

    @GetMapping("/user/{userId}")
    fun getWalletByUserId(@PathVariable userId: UUID): ResponseEntity<Any> {
        return try {
            val wallet = walletService.getWalletByUserId(userId)
            ResponseEntity.ok(wallet)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to e.message))
        }
    }
}