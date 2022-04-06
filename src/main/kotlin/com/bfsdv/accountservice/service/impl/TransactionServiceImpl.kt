package com.bfsdv.accountservice.service.impl

import com.bfsdv.accountservice.dto.*
import com.bfsdv.accountservice.entities.Account
import com.bfsdv.accountservice.entities.Transaction
import com.bfsdv.accountservice.exception.AccountException
import com.bfsdv.accountservice.exception.AccountNotFoundException
import com.bfsdv.accountservice.exception.TransactionNotFoundException
import com.bfsdv.accountservice.repository.AccountRepository
import com.bfsdv.accountservice.repository.TransactionRepository
import com.bfsdv.accountservice.service.AccountService
import com.bfsdv.accountservice.service.TransactionService
import com.bfsdv.accountservice.utils.*
import org.hibernate.TransactionException
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.Transactional
import java.sql.Date
import java.util.*

@Service
class TransactionServiceImpl(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) : TransactionService {

    companion object {
        private val log = LoggerFactory.getLogger(TransactionServiceImpl::class.java)
    }

    private fun mapEntityToTransactionDetailsDto(transaction: Transaction): TransactionDetailsDto {
        return TransactionDetailsDto(
            fromAccount = transaction.fromAccount,
            toAccount = transaction.toAccount,
            amount = transaction.amount,
            remarks = transaction.remarks,
            transactionId = transaction.transactionId,
            status = transaction.status,
            transactionDate = transaction.transactionDate
        )
    }

    private fun mapFundTransferRequestToEntity(fundTransferRequestDto: FundTransferRequestDto): Transaction {
        return Transaction(
            fromAccount = fundTransferRequestDto.fromAccount,
            toAccount = fundTransferRequestDto.toAccount,
            amount = fundTransferRequestDto.amount,
            remarks = fundTransferRequestDto.remarks,
            transactionId = UUID.randomUUID().toString(),
            status = TransactionStatus.SUCCESS.name,
            transactionDate = Date(System.currentTimeMillis())
        )
    }

    /**
     * Transaction Nature: The function handling the fund transfer
     * Annotated with @Transactional annotation
     * so that debit from source, credit to destination account
     * and the entry in transaction table are executed sequentially
     * and commited only when complete transaction is successful
     *
     * Concurrency: To handle concurrency added a version field with @Version annotation
     * so as to implement an OptimisticLocking on the accouts. If concurrent requests are
     * there then only the first one would be successful, others would return 409 CONFLICT
     */
    @Transactional
    override fun fundTransfer(request: FundTransferRequestDto): FundTransferResponseDto {
        // check if the fromAccount is a valid account else throw error
        var fromAccount = accountRepository.findById(request.fromAccount).orElseThrow { AccountNotFoundException(ACCOUNT_NOT_FOUND_ERROR_MESSAGE) }
        // check if the amount has the necessary funds for transfer
        if (fromAccount.balance!! < request.amount) {
            return FundTransferResponseDto(
                transactionId = null,
                status = TransactionStatus.FAILED.name,
                description = INSUFFICIENT_BALANCE
            )
        }
        // check if the toAccount is a valid account else throw error
        var toAccount = accountRepository.findById(request.toAccount).orElseThrow { AccountNotFoundException(ACCOUNT_NOT_FOUND_ERROR_MESSAGE) }
        //accountRepository.save(fromAccount)
        //accountRepository.save(toAccount)
        //no need to explicitly save the entities as the function is annotated with @Transaction
        fromAccount.balance = fromAccount.balance?.minus(request.amount)
        toAccount.balance = toAccount.balance?.plus(request.amount)
        try {
            val transactionEntity = mapFundTransferRequestToEntity(request)
            transactionRepository.save(transactionEntity)
            return FundTransferResponseDto(
                transactionId = transactionEntity.transactionId,
                status = TransactionStatus.SUCCESS.name,
                description = TRANSACTION_SUCCESSFUL
            )
        }
        catch (e: Exception) {
            log.error("Got error while fund trasfer: {}", e)
            throw TransactionException(FUND_TRANSFER_ERROR_MESSAGE)
        }
    }

    override fun fetchTransactionDetails(transactionId: String): TransactionDetailsDto {
        log.info("Fetching the transaction details for the ID: {}", transactionId)
        try {
            val transactionEntity = transactionRepository.findById(transactionId)
            val transaction = transactionEntity.orElseThrow{ TransactionNotFoundException(TRANSACTION_NOT_FOUND_ERROR_MESSAGE) }
            return mapEntityToTransactionDetailsDto(transaction)
        }
        catch (ex: DataAccessException) {
            log.error("Got error while fetch profile account details: {}", ex)
            throw AccountException(ACCOUNT_FETCH_ERROR_MESSAGE)
        }

    }
}