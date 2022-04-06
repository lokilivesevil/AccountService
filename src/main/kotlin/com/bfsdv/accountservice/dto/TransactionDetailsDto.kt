package com.bfsdv.accountservice.dto

import java.sql.Date

data class TransactionDetailsDto (
    val transactionId: String?,
    val fromAccount: String?,
    val toAccount: String?,
    val transactionDate: Date?,
    val status: String?,
    val remarks: String?,
    val amount: Double?
)