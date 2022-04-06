package com.bfsdv.accountservice.controller

import com.bfsdv.accountservice.dto.*
import com.bfsdv.accountservice.service.TransactionService
import com.bfsdv.accountservice.utils.APPLICATION_JSON
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(produces = [APPLICATION_JSON])

@Validated
class TransactionController (private val transactionService: TransactionService) {
    companion object {
        private val log = LoggerFactory.getLogger(TransactionController::class.java)
    }

    /**
     * Fund Transfer Endpoint
     */
    @PostMapping("/api/v1/transactions")
    fun fundTransfer(
        @Valid @RequestBody request: FundTransferRequestDto
    ): FundTransferResponseDto {
        log.debug("Performing Fund transfer Request from {} to {}", request.fromAccount, request.toAccount)
        return transactionService.fundTransfer(request)
    }

    /**
     * Fetch transaction details from transactionID
     */
    @GetMapping("/api/v1/transactions/{transactionId}")
    fun fetchTransactionDetails(@PathVariable(required = true, name = "transactionId") transactionId: String): TransactionDetailsDto? {
        log.info("fetching card codes for current customer")
        return transactionService.fetchTransactionDetails(transactionId)
    }

}