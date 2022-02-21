package com.hibay.goldking.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hibay.goldking.base.BaseViewModel
import com.hibay.goldking.bean.ReportListBean
import com.hibay.goldking.net.RetrofitClient

class MyReportViewModel : BaseViewModel() {

    val refreshStatus = MutableLiveData<Boolean>()
    val inspectionList = MutableLiveData<MutableList<ReportListBean>>()
    fun getReportList(flag: Int? = 0) {
        launch({
            refreshStatus.value = true
            inspectionList.value = RetrofitClient.apiService.getAppReportInfo(flag).apiData()
            refreshStatus.value = false
        }, error = { refreshStatus.value = false })
    }
}