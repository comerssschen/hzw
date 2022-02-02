package com.hibay.goldking.ui.act

import android.os.Bundle
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseActivity
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.common.isLogin


/**
 *
 *
 * @author chenchao
 * @date 2/26/21 11:34 AM
 */
class SplashActivity : BaseActivity(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.postDelayed({
            if (isLogin()) {
                ActivityHelper.startActivity(MainActivity::class.java)
            } else {
                ActivityHelper.startActivity(LoginActivity::class.java)
            }
            ActivityHelper.finish(SplashActivity::class.java)
        }, 1000)
    }
}