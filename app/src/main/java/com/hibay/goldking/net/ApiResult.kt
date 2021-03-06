package com.hibay.goldking.net

import androidx.annotation.Keep

@Keep
data class ApiResult<T>(val code: Int, val msg: String, private val data: T?) {
    fun apiData(): T {
        if (code == 0 && data != null) {
            return data
        } else {
            throw ApiException(code, msg)
        }
    }
}