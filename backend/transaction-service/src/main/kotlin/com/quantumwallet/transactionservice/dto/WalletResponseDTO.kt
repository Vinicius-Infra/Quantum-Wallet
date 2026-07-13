package com.quantumwallet.transactionservice.dto

import java.math.BigDecimal
import java.util.UUID

data class WalletResponseDTO(
    val id: UUID,
    val userId: UUID,
    val balance: BigDecimal,
    val type: String
)