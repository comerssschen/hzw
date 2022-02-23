package com.hibay.goldking.net

import com.hibay.goldking.bean.*
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@JvmSuppressWildcards
interface ApiService {

    companion object {
        const val BASE_URL = "http://47.101.165.93:8012"
    }

    @POST("user/appLogin")
    suspend fun login(@Body map: Map<String, Any?>): ApiResult<UserInfo?>

    //扫描二维码获取所有需巡检的设备信息接口
    @GET("inspection/getFacilityInfoList")
    suspend fun getFacilityInfoList(@Query("id") id: String?): ApiResult<FacilityInfoListBean>

    //根据巡检id修改当前设备的信息接口
    @POST("inspection/updateFacilityStatus")
    suspend fun updateFacilityStatus(@Body map: Map<String, Any?>): ApiResult<Int>

    //获取巡检列表接口
    @GET("inspection/getInspectionList")
    suspend fun getInspectionList(@Query("flag") flag: Int?): ApiResult<MutableList<InspectionListBean>?>

    //获取巡检列表接口
    @GET("inspection/queryFacilityInfoByGroupInspectionId")
    suspend fun queryFacilityInfoByGroupInspectionId(@Query("groupInspectionId") flag: String?): ApiResult<ArrayList<ReList>?>

    //获取我的上报列表信息
    @GET("tskWorkorderContent/getAppReportInfo")
    suspend fun getAppReportInfo(@Query("flag") flag: Int?): ApiResult<MutableList<ReportListBean>?>


    //获取当前登录人的工单信息
    @GET("tskWorkorderContent/getAppWorkOrderInfo")
    suspend fun getAppWorkOrderInfo(): ApiResult<ReportInfoBean?>

    //获取所有故障设备接口
    @GET("facility/errorFacilityList")
    suspend fun getErrorFacilityList(): ApiResult<MutableList<ErrorFacilityList>?>

    @POST("tskWorkorderContent/imgUpload")
    suspend fun uploadImage(@Body map: RequestBody): ApiResult<String>

}