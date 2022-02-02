package com.hibay.goldking.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hibay.goldking.base.BaseViewModel
import com.hibay.goldking.bean.FacilityInfoListBean
import com.hibay.goldking.bean.InspectionListBean
import com.hibay.goldking.bean.ReList
import com.hibay.goldking.net.RetrofitClient

class InspectionViewMoel : BaseViewModel() {
    val facilityInfoListResult = MutableLiveData<FacilityInfoListBean>()
    fun getFacilityInfoList(idStr: String) {
        launch({
            facilityInfoListResult.value = RetrofitClient.apiService.getFacilityInfoList(idStr).apiData()
        })
    }

    val refreshStatus = MutableLiveData<Boolean>()
    val inspectionList = MutableLiveData<MutableList<InspectionListBean>>()
    fun getInspectionList(flag: Int? = 0) {
        launch({
            refreshStatus.value = true
            inspectionList.value = RetrofitClient.apiService.getInspectionList(flag).apiData()
            refreshStatus.value = false
        }, error = { refreshStatus.value = false })
    }


    val facilityInfo = MutableLiveData<ReList>()
    fun getFacilityInfoById(id: String) {
        launch({
            facilityInfo.value = RetrofitClient.apiService.getFacilityInfoById("fc8a7c410df243bdb0a35d8a70fe0d7e").apiData()
        })
    }

    val updateFacilityResult = MutableLiveData<Int>()
    fun updateFacilityStatus(bean: ReList?, status: String, imageAddress: String? = null, faultDesc: String? = null) {
        launch({
            updateFacilityResult.value = RetrofitClient.apiService.updateFacilityStatus(
                mapOf("id" to bean?.id, "status" to status, "imageAddress" to imageAddress, "faultDesc" to faultDesc)
            ).apiData()
        })
    }
}