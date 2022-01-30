package com.hibay.goldking.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.hibay.goldking.base.BaseViewModel
import com.hibay.goldking.bean.UserInfo
import com.hibay.goldking.common.token
import com.hibay.goldking.net.RetrofitClient


/**
 *
 *
 * @author chenchao
 * @date 4/22/21 09:53
 */
class LoginViewModel : BaseViewModel() {

    val loginResult = MutableLiveData<UserInfo>()
    fun login(name: String, pwd: String) {
        launch({
            Gson().fromJson(RetrofitClient.apiService.login(mapOf("username" to name, "password" to pwd)).apiData(), UserInfo::class.java).let {
                token = it.token
                loginResult.value = it
            }
        })
    }
}