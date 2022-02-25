package com.hibay.goldking.ui.act

import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseVmActivity
import com.hibay.goldking.common.*
import com.hibay.goldking.ui.view.CommonDialog
import com.hibay.goldking.ui.viewmodel.LoginViewModel


/**
 *
 *
 * @author chenchao
 * @date 2/26/21 11:34 AM
 */
class SplashActivity : BaseVmActivity<LoginViewModel>(R.layout.activity_splash) {
    override fun viewModelClass() = LoginViewModel::class.java

    override fun initData() {
        super.initData()
        mViewModel.judgeUpgrade()
    }

    override fun observe() {
        super.observe()
        mViewModel.downUrl.observe(this) {
            if (it.isEmpty()) {
                if (isLogin()) {
                    mViewModel.login(accountName, accountPWD)
                } else {
                    ActivityHelper.startActivity(LoginActivity::class.java)
                    ActivityHelper.finish(SplashActivity::class.java)
                }
            } else {
                CommonDialog(this, "提示", "已有新版本，是否升级？ \n点击确认进行升级", {
                    PermissionUtils.permission(PermissionConstants.STORAGE)
                        .callback(object : PermissionUtils.SimpleCallback {
                            override fun onGranted() {
                                showToast("下载中，请稍后")
                                mViewModel.downApk(it)
                            }

                            override fun onDenied() {
                                showToast("权限不足")
                                if (isLogin()) {
                                    mViewModel.login(accountName, accountPWD)
                                } else {
                                    ActivityHelper.startActivity(LoginActivity::class.java)
                                    ActivityHelper.finish(SplashActivity::class.java)
                                }
                            }
                        }).request()
                }, {
                    if (isLogin()) {
                        mViewModel.login(accountName, accountPWD)
                    } else {
                        ActivityHelper.startActivity(LoginActivity::class.java)
                        ActivityHelper.finish(SplashActivity::class.java)
                    }
                }).show()
            }
        }
        mViewModel.loginSucces.observe(this@SplashActivity) {
            if (it) {
                ActivityHelper.startActivity(MainActivity::class.java)
            } else {
                ActivityHelper.startActivity(LoginActivity::class.java)
            }
            ActivityHelper.finish(SplashActivity::class.java)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}