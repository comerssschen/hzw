package com.hibay.goldking.ui.act

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import com.blankj.utilcode.util.BarUtils
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseVmActivity
import com.hibay.goldking.common.*
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
        BarUtils.setStatusBarColor(this, resources.getColor(R.color.white))
        etLoginName.addTextChangedListener {
            setButtonEnable()
        }
        etLoginPWD.addTextChangedListener {
            setButtonEnable()
        }

        etLoginPWD.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                btLogin.performClick()
                true
            } else {
                false
            }
        }
        btLogin.setOnClickListener {
            val phoneNum = etLoginName.text.toString().trim()
            val pwd = etLoginPWD.text.toString().trim()
            when {
                phoneNum.isEmpty() -> showToast(getString(R.string.login_name))
                pwd.isEmpty() -> showToast(getString(R.string.login_pwd))
                else -> {
                    mViewModel.login(phoneNum, pwd)
                }
            }
        }

        tvPrivacy.setOnClickListener {
            showToast("杭州湾运维协议")
        }
        tvForgetPWD.setOnClickListener {
            ActivityHelper.startActivity(ForgetPWDActivity::class.java)
        }
//        etLoginName.setText(accountName)
//        etLoginPWD.setText(accountPWD)
        setButtonEnable()
    }

    private fun setButtonEnable() {
        btLogin.isEnabled = etLoginName.toStr().isNotEmpty() && etLoginPWD.toStr().isNotEmpty()
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