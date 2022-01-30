package com.hibay.goldking.common

import com.blankj.utilcode.util.SPUtils

fun isLogin() = token.isNotEmpty() && mobile.isNotEmpty()

fun loginOut() {
    SPUtils.getInstance().remove("token")
    SPUtils.getInstance().remove("mobile")
}

var token: String
    get() = SPUtils.getInstance().getString("token")
    set(value) = SPUtils.getInstance().put("token", value)

var mobile: String
    get() = SPUtils.getInstance().getString("mobile")
    set(value) = SPUtils.getInstance().put("mobile", value)
