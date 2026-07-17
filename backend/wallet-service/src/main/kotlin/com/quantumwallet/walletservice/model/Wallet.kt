package com.quantumwallet.walletservice.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "tb_wallets")
class Wallet(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "user_id", nullable = false, unique = true)
    val userId: UUID,

    @Column(nullable = false)
    var balance: BigDecimal = BigDecimal.ZERO,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: WalletType
) {
    protected constructor() : this(
        id = null,
        userId = UUID.randomUUID(),
        balance = BigDecimal.ZERO,
        type = WalletType.PERSONAL
    )
}