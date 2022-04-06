package com.bfsdv.accountservice.service.impl

import com.bfsdv.accountservice.dto.AccountCreationRequestDto
import com.bfsdv.accountservice.dto.AccountCreationResponseDto
import com.bfsdv.accountservice.entities.Account
import com.bfsdv.accountservice.exception.AccountException
import com.bfsdv.accountservice.exception.AccountNotFoundException
import com.bfsdv.accountservice.repository.AccountRepository

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.*
import org.mockito.*
import org.springframework.dao.DataAccessException
import java.lang.RuntimeException
import java.sql.Date
import java.util.*

internal class AccountServiceImplTest {

    @InjectMocks
    lateinit var accountServiceImpl: AccountServiceImpl

    @Mock
    lateinit var accountRepository: AccountRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testWhenExceptionThrownInCreateAccountRequest() {
        Mockito.`when`(
            accountRepository.save(Mockito.any<Account>())
        ).thenThrow(RuntimeException())

        assertThrows<AccountException> { accountServiceImpl.createAccount(
            AccountCreationRequestDto(
            accountHolderName = "Test User 1",
            balance = 10213.00,
            mobileNumber = "9569234234"
        )
        ) }
    }

    @Test
    fun testCreateAccountRequestSuccessCase() {
        val response = accountServiceImpl.createAccount(
            AccountCreationRequestDto(
                accountHolderName = "Test User 1",
                balance = 10213.00,
                mobileNumber = "9569234234"
            )
        )
        Assertions.assertNotNull(response.accountNumber)
    }

    @Test
    fun testFetchProfileNotFoundScenario() {
        Mockito.`when`(
            accountRepository.findById(Mockito.any<String>())
        ).thenReturn(Optional.empty<Account>())

        assertThrows<AccountNotFoundException> { accountServiceImpl.fetchProfileDetails("123123123213") }
    }

    @Test
    fun testFetchAccountDetailsSuccessScenario() {
        Mockito.`when`(
            accountRepository.findById(Mockito.any<String>())
        ).thenReturn(Optional.of(Account(
            accountHolderName = "Test Entity",
            accountNumber = "123123123213",
            balance = 10.00,
            mobileNumber = "97123723111",
            openingDate = Date(System.currentTimeMillis()),
            version = 1
        )))

        val response = accountServiceImpl.fetchProfileDetails("123123123213")
        Assertions.assertNotNull(response.accountNumber)
    }
}