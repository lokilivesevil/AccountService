package com.bfsdv.accountservice.service

import com.bfsdv.accountservice.dto.*

interface TransactionService {
    fun fundTransfer(request: FundTransferRequestDto): FundTransferResponseDto
    fun fetchTransactionDetails(transactionId: String): TransactionDetailsDto
}