package com.bfsdv.accountservice.service.impl

import com.bfsdv.accountservice.dto.AccountCreationRequestDto
import com.bfsdv.accountservice.dto.AccountCreationResponseDto
import com.bfsdv.accountservice.dto.AccountDetailsResponseDto
import com.bfsdv.accountservice.entities.Account
import com.bfsdv.accountservice.exception.AccountException
import com.bfsdv.accountservice.exception.AccountNotFoundException
import com.bfsdv.accountservice.repository.AccountRepository
import com.bfsdv.accountservice.service.AccountService
import com.bfsdv.accountservice.utils.ACCOUNT_CREATION_ERROR_MESSAGE
import com.bfsdv.accountservice.utils.ACCOUNT_FETCH_ERROR_MESSAGE
import com.bfsdv.accountservice.utils.ACCOUNT_NOT_FOUND_ERROR_MESSAGE
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Service
import java.sql.Date
import java.util.*

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository
) : AccountService {

    companion object {
        private val log = LoggerFactory.getLogger(AccountServiceImpl::class.java)
    }


    override fun createAccount(request: AccountCreationRequestDto): AccountCreationResponseDto {
        log.info("Mapping the request to the account entity object")
        val accountEntity = mapAccountCreationRequestToEntity(request)
        try {
            accountRepository.save(accountEntity)
            return mapEntityToAccountCreationResponseDto(accountEntity)
        }
        catch (ex: Exception) {
            log.error("Got error while saving account details: {}", ex)
            throw AccountException(ACCOUNT_CREATION_ERROR_MESSAGE)
        }
    }

    override fun fetchProfileDetails(accountNumber: String): AccountDetailsResponseDto {
        log.info("Fetching the details from the database for account number: {}", accountNumber)
        try {
            val accountEntity = accountRepository.findById(accountNumber)
            val account = accountEntity.orElseThrow{AccountNotFoundException(ACCOUNT_NOT_FOUND_ERROR_MESSAGE)}
            return mapEntityToAccountDetailsResponseDto(account)
        }
        catch (ex: DataAccessException) {
            log.error("Got error while fetch profile account details: {}", ex)
            throw AccountException(ACCOUNT_FETCH_ERROR_MESSAGE)
        }
    }

    private fun mapAccountCreationRequestToEntity(accountCreationRequestDto: AccountCreationRequestDto): Account {
        return Account(
            accountHolderName = accountCreationRequestDto.accountHolderName,
            accountNumber = UUID.randomUUID().toString(),
            balance = accountCreationRequestDto.balance,
            mobileNumber = accountCreationRequestDto.mobileNumber,
            openingDate = Date(System.currentTimeMillis()),
            version = 1
        )
    }

    private fun mapEntityToAccountCreationResponseDto(account: Account): AccountCreationResponseDto {
        return AccountCreationResponseDto(
            accountHolderName = account.accountHolderName,
            accountNumber = account.accountNumber,
            balance = account.balance,
            mobileNumber = account.mobileNumber,
            openingDate = account.openingDate
        )
    }

    private fun mapEntityToAccountDetailsResponseDto(account: Account): AccountDetailsResponseDto {
        return AccountDetailsResponseDto(
            accountHolderName = account.accountHolderName,
            accountNumber = account.accountNumber,
            balance = account.balance,
            mobileNumber = account.mobileNumber,
            openingDate = account.openingDate
        )
    }
}