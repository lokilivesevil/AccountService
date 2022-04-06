package com.bfsdv.accountservice.dto

import com.bfsdv.accountservice.utils.INVALID_MOBILENUMBER_ERROR
import com.bfsdv.accountservice.utils.INVALID_NAME_ERROR
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class AccountCreationRequestDto (
    @NotBlank(message = INVALID_NAME_ERROR)
    @NotNull(message = INVALID_NAME_ERROR)
    val accountHolderName: String,
    @NotBlank(message = INVALID_MOBILENUMBER_ERROR)
    @NotNull(message = INVALID_MOBILENUMBER_ERROR)
    val mobileNumber: String,
    val balance: Double? = 0.0,
)