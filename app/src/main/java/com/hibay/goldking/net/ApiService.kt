package com.hibay.goldking.net

import com.hibay.goldking.bean.*
import retrofit2.http.Body
import retrofit2.http.POST

@JvmSuppressWildcards
interface ApiService {

    companion object {
        const val BASE_URL = "http://47.101.165.93:8012"
    }

    @POST("user/login")
    suspend fun login(@Body map: Map<String, Any?>): ApiResult<String>
}