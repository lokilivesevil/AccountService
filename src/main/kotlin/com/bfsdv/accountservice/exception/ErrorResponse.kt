package com.bfsdv.accountservice.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.util.*

class ErrorResponse(
    val code: String,
    val message: String,
)