package com.bfsdv.accountservice.dto

import java.sql.Date

data class AccountDetailsResponseDto (
    val accountHolderName: String?,
    val accountNumber: String?,
    val mobileNumber: String?,
    val openingDate: Date?,
    val balance: Double?,
)