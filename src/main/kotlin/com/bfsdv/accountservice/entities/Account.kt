package com.bfsdv.accountservice.entities

import lombok.Builder
import lombok.Data
import org.hibernate.annotations.CreationTimestamp
import org.springframework.format.annotation.DateTimeFormat
import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "account")
data class Account(
    @Id
    @Column(name = "accountNumber")
    var accountNumber: String?,

    @Column(name = "openingDate")
    var openingDate: Date?,

    @Column(name = "mobile")
    var mobileNumber: String?,

    @Column(name = "name")
    var accountHolderName: String?,

    @Column(name = "balance")
    var balance: Double?,

    @Version
    @Column(name = "version")
    var version: Int,
)
