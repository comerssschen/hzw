package com.hibay.goldking.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hibay.goldking.base.BaseViewModel
import com.hibay.goldking.bean.ReportInfoBean
import com.hibay.goldking.net.RetrofitClient

class MineFragmentViewModel : BaseViewModel() {

    val infoResult = MutableLiveData<ReportInfoBean>()
    fun getAppWorkOrderInfo() {
        launch({
            infoResult.value = RetrofitClient.apiService.getAppWorkOrderInfo().apiData()
        }, isShowLoadding = false)
    }
}