package com.bfsdv.accountservice.exception

import com.bfsdv.accountservice.utils.GENERIC_ERROR_MESSAGE


class FundTransferException(message: String? = GENERIC_ERROR_MESSAGE) : RuntimeException(message)