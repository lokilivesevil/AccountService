package com.bfsdv.accountservice.dto

import com.bfsdv.accountservice.utils.INVALID_ACCOUNTNUMBER_ERROR
import com.bfsdv.accountservice.utils.INVALID_AMOUNT
import com.bfsdv.accountservice.utils.INVALID_MOBILENUMBER_ERROR
import com.bfsdv.accountservice.utils.INVALID_NAME_ERROR
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class FundTransferRequestDto (
    @NotBlank(message = INVALID_ACCOUNTNUMBER_ERROR)
    @NotNull(message = INVALID_ACCOUNTNUMBER_ERROR)
    val fromAccount: String,
    @NotBlank(message = INVALID_ACCOUNTNUMBER_ERROR)
    @NotNull(message = INVALID_ACCOUNTNUMBER_ERROR)
    val toAccount: String,
    @NotNull(message = INVALID_AMOUNT)
    val amount: Double,
    val remarks: String?,
)