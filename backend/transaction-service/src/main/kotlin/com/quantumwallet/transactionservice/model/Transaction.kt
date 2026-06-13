package com.quantumwallet.transactionservice.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tb_transactions")
class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false)
    val sourceWalletId: UUID,

    @Column(nullable = false)
    val destinationWalletId: UUID,

    @Column(nullable = false)
    val amount: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: TransactionType,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)