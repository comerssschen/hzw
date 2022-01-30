package com.hibay.goldking.ui.act

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.blankj.utilcode.util.BarUtils
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseVmActivity
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.common.showToast
import com.hibay.goldking.ui.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*


/**
 *
 *
 * @author chenchao
 * @date 3/2/21 13:31
 */
class LoginActivity : BaseVmActivity<LoginViewModel>(R.layout.activity_login) {
    override fun viewModelClass() = LoginViewModel::class.java
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.transparentStatusBar(this)
        BarUtils.addMarginTopEqualStatusBarHeight(tv_header)

        et_logincode.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                bt_login.performClick()
                true
            } else {
                false
            }
        }
        bt_login.setOnClickListener {
            val phoneNum = et_loginname.text.toString().trim()
            val pwd = et_logincode.text.toString().trim()
            when {
                phoneNum.isEmpty() -> showToast(getString(R.string.login_name))
                pwd.isEmpty() -> showToast(getString(R.string.login_code))
                else -> {
                    mViewModel.login(phoneNum, pwd)
                }

            }

        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            loginResult.observe(this@LoginActivity) {
                ActivityHelper.startActivity(MainActivity::class.java)
                ActivityHelper.finish(LoginActivity::class.java)
            }
        }
    }
}