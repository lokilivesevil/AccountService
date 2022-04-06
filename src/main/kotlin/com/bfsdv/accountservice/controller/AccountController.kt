package com.bfsdv.accountservice.controller

import com.bfsdv.accountservice.dto.AccountCreationRequestDto
import com.bfsdv.accountservice.dto.AccountCreationResponseDto
import com.bfsdv.accountservice.dto.AccountDetailsResponseDto
import com.bfsdv.accountservice.service.AccountService
import com.bfsdv.accountservice.utils.APPLICATION_JSON

import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(produces = [APPLICATION_JSON])

@Validated
class AccountController(private val accountService: AccountService) {
    companion object {
        private val log = LoggerFactory.getLogger(AccountController::class.java)
    }

    /**
     * Create account endpoint
     */
    @PostMapping("/api/v1/accounts")
    fun createAccount(
        @Valid @RequestBody request: AccountCreationRequestDto): AccountCreationResponseDto {
        log.debug("Create account for customer : {}", request.mobileNumber)
        return accountService.createAccount(request)
    }

    /**
     * Fetch profile details from account number
    */
    @GetMapping("/api/v1/accounts/{accountNumber}")
    fun fetchProfile(@PathVariable(required = true, name = "accountNumber") accountNumber: String): AccountDetailsResponseDto? {
        log.info("fetching card codes for current customer")
        return accountService.fetchProfileDetails(accountNumber)
    }

}