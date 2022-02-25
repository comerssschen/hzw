package com.hibay.goldking.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hibay.goldking.base.BaseViewModel
import com.hibay.goldking.bean.FacilityInfoListBean
import com.hibay.goldking.bean.InspectionListBean
import com.hibay.goldking.bean.ReList
import com.hibay.goldking.net.RetrofitClient
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class InspectionViewMoel : BaseViewModel() {
    val facilityInfoListResult = MutableLiveData<FacilityInfoListBean>()
    val failResult = MutableLiveData<Boolean>()
    fun getFacilityInfoList(idStr: String?) {
        launch({
            facilityInfoListResult.value = RetrofitClient.apiService.getFacilityInfoList(idStr).apiData().apply {
                this.qrCodeIds = idStr
            }
        }, {
            delay(1000)
            failResult.value = true
        })
    }

    val refreshStatus = MutableLiveData<Boolean>()
    val inspectionList = MutableLiveData<MutableList<InspectionListBean>>()
    fun getInspectionList(flag: Int? = 0) {
        launch({
            refreshStatus.value = true
            inspectionList.value = RetrofitClient.apiService.getInspectionList(flag).apiData()
            refreshStatus.value = false
        }, isShowLoadding = false, error = { refreshStatus.value = false })
    }

    val updateFacilityResult = MutableLiveData<Int>()
    fun updateFacilityStatus(bean: ReList?, status: String, imageAddress: String? = null, faultDesc: String? = null, isMaintain: Boolean = false) {
        launch({
            updateFacilityResult.value = RetrofitClient.apiService.updateFacilityStatus(
                mapOf(
                    "id" to bean?.id, "status" to status, "imageAddress" to imageAddress, "faultDesc" to faultDesc, "maintain" to if (isMaintain) {
                        1
                    } else {
                        0
                    }
                )
            ).apiData()
        })
    }

    val GroupInspectionIdList = MutableLiveData<ArrayList<ReList>>()
    fun queryFacilityInfoByGroupInspectionId(groupID: String) {
        launch({
            GroupInspectionIdList.value = RetrofitClient.apiService.queryFacilityInfoByGroupInspectionId(groupID).apiData()
        })
    }

    val uploadImageResult = MutableLiveData<String>()
    fun uploadImage(file: File) {
        val fileRQ = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Builder()
            .addFormDataPart("file", file.name, fileRQ)
            .build()
        launch({
            uploadImageResult.value = RetrofitClient.apiService.uploadImage(body).apiData()
        })
    }
}