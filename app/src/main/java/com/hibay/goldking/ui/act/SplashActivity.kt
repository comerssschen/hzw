package com.hibay.goldking.ui.act

import android.os.Bundle
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseVmActivity
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.common.accountName
import com.hibay.goldking.common.accountPWD
import com.hibay.goldking.common.isLogin
import com.hibay.goldking.ui.viewmodel.LoginViewModel


/**
 *
 *
 * @author chenchao
 * @date 2/26/21 11:34 AM
 */
class SplashActivity : BaseVmActivity<LoginViewModel>(R.layout.activity_splash) {
    override fun viewModelClass() = LoginViewModel::class.java
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.postDelayed({
            if (isLogin()) {
                mViewModel.login(accountName, accountPWD)
            } else {
                ActivityHelper.startActivity(LoginActivity::class.java)
                ActivityHelper.finish(SplashActivity::class.java)
            }
        }, 1000)
    }

    override fun observe() {
        super.observe()
        mViewModel.loginSucces.observe(this@SplashActivity) {
            if (it) {
                ActivityHelper.startActivity(MainActivity::class.java)
            } else {
                ActivityHelper.startActivity(LoginActivity::class.java)
            }
            ActivityHelper.finish(SplashActivity::class.java)
        }
    }
}