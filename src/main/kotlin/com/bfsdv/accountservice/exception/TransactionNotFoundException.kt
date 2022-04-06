package com.bfsdv.accountservice.exception

import com.bfsdv.accountservice.utils.ACCOUNT_NOT_FOUND_ERROR_CODE
import com.bfsdv.accountservice.utils.ACCOUNT_NOT_FOUND_ERROR_MESSAGE
import com.bfsdv.accountservice.utils.TRANSACTION_NOT_FOUND_ERROR_CODE
import com.bfsdv.accountservice.utils.TRANSACTION_NOT_FOUND_ERROR_MESSAGE


class TransactionNotFoundException(code: String? = TRANSACTION_NOT_FOUND_ERROR_CODE, message: String? = TRANSACTION_NOT_FOUND_ERROR_MESSAGE) : RuntimeException(message)