package com.hibay.goldking.net

import com.hibay.goldking.bean.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@JvmSuppressWildcards
interface ApiService {

    companion object {
        const val BASE_URL = "http://47.101.165.93:8012"
    }

    @POST("user/login")
    suspend fun login(@Body map: Map<String, Any?>): ApiResult<String?>

    //扫描二维码获取所有需巡检的设备信息接口
    @GET("inspection/getFacilityInfoList")
    suspend fun getFacilityInfoList(@Query("id") id: String): ApiResult<FacilityInfoListBean>

    //根据巡检id,获取当前设备的详细信息接口
    @GET("inspection/getFacilityInfoById")
    suspend fun getFacilityInfoById(@Query("id") id: String): ApiResult<ReList>

    //根据巡检id修改当前设备的信息接口
    @POST("inspection/updateFacilityStatus")
    suspend fun updateFacilityStatus(@Body map: Map<String, Any?>): ApiResult<Int>

    //获取巡检列表接口
    @GET("inspection/getInspectionList")
    suspend fun getInspectionList(@Query("flag") flag: Int?): ApiResult<MutableList<InspectionListBean>?>

    @POST("")
    suspend fun uploadImage()

}