package com.bfsdv.accountservice.exception

import com.bfsdv.accountservice.utils.ACCOUNT_NOT_FOUND_ERROR_CODE
import com.bfsdv.accountservice.utils.ACCOUNT_NOT_FOUND_ERROR_MESSAGE


class AccountNotFoundException(code: String? = ACCOUNT_NOT_FOUND_ERROR_CODE, message: String? = ACCOUNT_NOT_FOUND_ERROR_MESSAGE) : RuntimeException(message)