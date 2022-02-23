package com.hibay.goldking.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hibay.goldking.base.BaseViewModel
import com.hibay.goldking.bean.ErrorFacilityList
import com.hibay.goldking.net.RetrofitClient

class ErrorFacilityViewModel : BaseViewModel() {
    val refreshStatus = MutableLiveData<Boolean>()
    val errorFacilityListReslt = MutableLiveData<MutableList<ErrorFacilityList>>()
    fun getErrorFacilityList() {
        launch({
            refreshStatus.value = true
            errorFacilityListReslt.value = RetrofitClient.apiService.getErrorFacilityList().apiData()
            refreshStatus.value = false
        }, isShowLoadding = false, error = { refreshStatus.value = false })
    }
}