package com.bfsdv.accountservice.service.impl

import com.bfsdv.accountservice.dto.FundTransferRequestDto
import com.bfsdv.accountservice.entities.Account
import com.bfsdv.accountservice.exception.AccountNotFoundException
import com.bfsdv.accountservice.repository.AccountRepository
import com.bfsdv.accountservice.utils.INSUFFICIENT_BALANCE
import com.bfsdv.accountservice.utils.TRANSACTION_SUCCESSFUL
import com.bfsdv.accountservice.utils.TransactionStatus
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.test.context.event.annotation.AfterTestMethod
import java.sql.Date
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws


@SpringBootTest
internal class TransactionServiceImplTest {

    @Autowired
    lateinit var transactionServiceImpl: TransactionServiceImpl

    @Autowired
    lateinit var accountRepository: AccountRepository

    @Test()
    @Order(1)
    fun testFundTransferWithIncorrectFromAccount() {
        assertThrows <AccountNotFoundException> { transactionServiceImpl.fundTransfer(FundTransferRequestDto(
            fromAccount = "1231231-123-123-1232-2233",
            toAccount = "123123-349945-29391923-4545",
            amount = 15.00,
            remarks = "testing transaction"
        )) }
    }

    @Test()
    @Order(2)
    fun testFundTransferWithInsufficientBalance() {
        accountRepository.save(
            Account(
                accountHolderName = "Test Entity",
                accountNumber = "1231231-123-123-1232-2233",
                balance = 10.00,
                mobileNumber = "97123723111",
                openingDate = Date(System.currentTimeMillis()),
                version = 1
            )
        )

        val fundTransferResponse = transactionServiceImpl.fundTransfer(FundTransferRequestDto(
            fromAccount = "1231231-123-123-1232-2233",
            toAccount = "123123-349945-29391923-4545",
            amount = 15.00,
            remarks = "testing transaction"
        ))

        assertEquals(fundTransferResponse.description, INSUFFICIENT_BALANCE)
    }

    @Test()
    @Order(3)
    fun testFundTransferWithIncorrectToAccount() {
        assertThrows <AccountNotFoundException> { transactionServiceImpl.fundTransfer(FundTransferRequestDto(
            fromAccount = "1231231-123-123-1232-2233",
            toAccount = "123123-349945-29391923-4545",
            amount = 5.00,
            remarks = "testing transaction"
        )) }
    }

    @Test()
    @Order(4)
    fun testFundTransferSuccessFlow() {
        accountRepository.save(
            Account(
                accountHolderName = "Test Entity2",
                accountNumber = "123123-349945-29391923-4545",
                balance = 0.00,
                mobileNumber = "97123723111",
                openingDate = Date(System.currentTimeMillis()),
                version = 1
            )
        )

        val fundTransferResponse = transactionServiceImpl.fundTransfer(FundTransferRequestDto(
            fromAccount = "1231231-123-123-1232-2233",
            toAccount = "123123-349945-29391923-4545",
            amount = 5.00,
            remarks = "testing transaction"
        ))

        assertEquals(fundTransferResponse.description, TRANSACTION_SUCCESSFUL)
        assertEquals(fundTransferResponse.status, TransactionStatus.SUCCESS.name)
        //FindById
    }

    @Test
    @Order(5)
    fun testFundTransferZOptimisticLockingHandling() {
        /**
         * Using the Executer to create 2 concurrent fund transfer requests on same accounts
         * Only 1 of them would be successful
         */

        accountRepository.save(
            Account(
                accountHolderName = "Test Entity 1",
                accountNumber = "2231231-123-123-1232-2233",
                balance = 10.00,
                mobileNumber = "97123723111",
                openingDate = Date(System.currentTimeMillis()),
                version = 1
            )
        )
        accountRepository.save(
            Account(
                accountHolderName = "Test Entity 2",
                accountNumber = "2231231-123-123-1232-2234",
                balance = 10.00,
                mobileNumber = "97123723111",
                openingDate = Date(System.currentTimeMillis()),
                version = 1
            )
        )
        val threadArray = Arrays.asList(1, 1);
        val executor = Executors.newFixedThreadPool(threadArray.size)
        for (amount in threadArray) {
            executor.execute { transactionServiceImpl.fundTransfer(FundTransferRequestDto(
                fromAccount = "2231231-123-123-1232-2233",
                toAccount = "2231231-123-123-1232-2234",
                amount = amount.toDouble(),
                remarks = "testing transaction"
            )) }
        }
        executor.shutdown()
        executor.awaitTermination(1, TimeUnit.MINUTES)
        val account1Details = accountRepository.findById("2231231-123-123-1232-2233")
        val account2Details = accountRepository.findById("2231231-123-123-1232-2234")
        assertEquals(account1Details.get().balance, 9.00)
        assertEquals(account2Details.get().balance, 11.00)
    }
}