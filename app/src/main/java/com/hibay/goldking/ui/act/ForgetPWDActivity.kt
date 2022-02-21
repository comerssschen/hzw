package com.hibay.goldking.ui.act

import androidx.core.widget.addTextChangedListener
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseVmActivity
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.common.showToast
import com.hibay.goldking.common.toStr
import com.hibay.goldking.ui.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_forget_pwd.*

class ForgetPWDActivity : BaseVmActivity<LoginViewModel>(R.layout.activity_forget_pwd) {
    override fun viewModelClass() = LoginViewModel::class.java
    override fun initView() {
        super.initView()
        tvBack.setOnClickListener { ActivityHelper.finish(ForgetPWDActivity::class.java) }
        etPhone.addTextChangedListener { btIsEnable() }
        etCode.addTextChangedListener { btIsEnable() }
        etNewPwd.addTextChangedListener { btIsEnable() }
        etNewPwd2.addTextChangedListener { btIsEnable() }

        tvGetCode.setOnClickListener { showToast("获取验证码") }
        btLogin.setOnClickListener {
            if (etNewPwd.toStr() == etNewPwd2.toStr()) {
                mViewModel
            } else {
                showToast("两次密码不一致")
            }
        }
    }

    fun btIsEnable() {
        btLogin.isEnabled = etPhone.toStr().isNotEmpty() && etCode.toStr().isNotEmpty() && etNewPwd.toStr().isNotEmpty() && etNewPwd2.toStr().isNotEmpty()
    }
}