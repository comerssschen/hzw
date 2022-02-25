package com.hibay.goldking.ui.viewmodel

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.FileIOUtils
import com.google.gson.Gson
import com.hibay.goldking.base.BaseViewModel
import com.hibay.goldking.base.MyApplication
import com.hibay.goldking.bean.UserInfo
import com.hibay.goldking.common.*
import com.hibay.goldking.net.RetrofitClient
import okhttp3.ResponseBody
import java.io.File
import java.lang.Exception
import kotlin.math.min


/**
 *
 *
 * @author chenchao
 * @date 4/22/21 09:53
 */
class LoginViewModel : BaseViewModel() {

    val loginSucces = MutableLiveData<Boolean>()
    val loginResult = MutableLiveData<UserInfo>()
    fun login(name: String, pwd: String) {
        launch({
            RetrofitClient.apiService.login(mapOf("phone" to name, "password" to pwd)).apiData()?.let { userinfo ->
                userInfo = Gson().toJson(userinfo)
                accountName = name
                accountPWD = pwd
                token = userinfo.token
                loginResult.value = userinfo
                loginSucces.value = true
            }
        }, error = {
            loginSucces.value = false
        })
    }

    fun downApk(url: String) {
        launch({
            RetrofitClient.apiService.downloadFile(url).let {
                val filePath: String = MyApplication.instance.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.absolutePath.toString() + File.separator + "hzw.apk"
                saveFile(it, File(filePath))
            }
        })
    }

    private fun saveFile(responseBody: ResponseBody, file: File) {
        FileIOUtils.writeFileFromIS(file, responseBody.byteStream()).apply {
            if (this) {
                showToast("安装包已下载完成，正在启动安装")
                AppUtils.installApp(file)
            }
        }
    }


    val downUrl = MutableLiveData<String>()
    fun judgeUpgrade() {
        launch({
            RetrofitClient.apiService.judgeUpgrade(AppUtils.getAppVersionName()).apiData()?.let {
                val checkVersionValue = checkVersion(it.newVersion, AppUtils.getAppVersionName())
                if (checkVersionValue > 0) {
                    downUrl.value = it.address
                } else {
                    downUrl.value = ""
                }
            }
        }, error = {
            downUrl.value = ""
        }, showErrorToast = false)
    }


    private fun checkVersion(v1: String, v2: String): Int {
        var idx = 0
        try {
            val verArr1 = v1.split(".").toTypedArray()
            val verArr2 = v2.split(".").toTypedArray()
            val minLength = min(verArr1.size, verArr2.size)
            for (i in 0 until minLength) { //典型for循环
                var ver1 = verArr1[i].toInt()
                var ver2 = verArr2[i].toInt()
                if (ver1 == ver2) {
                    continue
                }
                idx = if (ver1 > ver2) {
                    1
                } else {
                    -1
                }
                break
            }
            if (idx == 0) {
                if (verArr1.size > verArr2.size) {
                    idx = 1;
                }
                if (verArr1.size < verArr2.size) {
                    idx = -1
                }
            }
        } catch (e: Exception) {
        }
        return idx;
    }
}