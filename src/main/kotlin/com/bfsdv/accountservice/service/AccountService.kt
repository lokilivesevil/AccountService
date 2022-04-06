package com.bfsdv.accountservice.service

import com.bfsdv.accountservice.dto.AccountCreationRequestDto
import com.bfsdv.accountservice.dto.AccountCreationResponseDto
import com.bfsdv.accountservice.dto.AccountDetailsResponseDto

interface AccountService {
    fun createAccount(request: AccountCreationRequestDto): AccountCreationResponseDto
    fun fetchProfileDetails(accountNumber: String): AccountDetailsResponseDto
}