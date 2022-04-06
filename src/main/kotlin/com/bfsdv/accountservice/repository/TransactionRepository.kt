package com.bfsdv.accountservice.repository

import com.bfsdv.accountservice.entities.Account
import com.bfsdv.accountservice.entities.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, String> {
}