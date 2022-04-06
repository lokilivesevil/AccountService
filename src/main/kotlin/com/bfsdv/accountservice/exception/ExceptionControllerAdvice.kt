package com.bfsdv.accountservice.exception

import com.bfsdv.accountservice.utils.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {
    @ExceptionHandler(AccountException::class)
    fun accountException(e: AccountException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.internalServerError().body(
                ErrorResponse(ACCOUNT_ERROR_CODE, e.message!!)
            )
    }

    @ExceptionHandler(FundTransferException::class)
    fun fundTransferException(e: FundTransferException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.internalServerError().body(
            ErrorResponse(FUND_TRANSFER_ERROR_CODE, e.message!!)
        )
    }

    @ExceptionHandler(AccountNotFoundException::class)
    fun accountNotFoundException(e: AccountNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse(ACCOUNT_NOT_FOUND_ERROR_CODE, e.message!!)
        )
    }

    @ExceptionHandler(TransactionNotFoundException::class)
    fun transactionNotFoundException(e: TransactionNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse(TRANSACTION_NOT_FOUND_ERROR_CODE, e.message!!)
        )
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException::class)
    fun objectOptimisticLockingFailureException(e: ObjectOptimisticLockingFailureException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ErrorResponse(CONCURRENT_TRANSACTION_ERROR_CODE, CONCURRENT_TRANSACTION_ERROR_MESSAGE)
        )
    }

}