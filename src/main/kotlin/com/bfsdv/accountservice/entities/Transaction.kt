package com.bfsdv.accountservice.entities

import lombok.Builder
import lombok.Data
import org.hibernate.annotations.CreationTimestamp
import org.springframework.format.annotation.DateTimeFormat
import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "transaction")
data class Transaction(
    @Id
    @Column(name = "transactionId")
    var transactionId: String?,

    @Column(name = "transactionDate")
    var transactionDate: Date?,

    @Column(name = "fromAccount")
    var fromAccount: String?,

    @Column(name = "toAccount")
    var toAccount: String?,

    @Column(name = "amount")
    var amount: Double?,

    @Column(name = "status")
    var status: String?,

    @Column(name = "remarks")
    var remarks: String?,
)
