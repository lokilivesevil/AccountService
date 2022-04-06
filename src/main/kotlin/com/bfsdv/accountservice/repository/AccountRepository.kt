package com.bfsdv.accountservice.repository

import com.bfsdv.accountservice.entities.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, String> {
}