package com.hibay.goldking.ui.act

import android.os.Bundle
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.TimeUtils
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseActivity
import com.hibay.goldking.bean.BusinessGroupBean
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.ui.adapter.BusinessGroupAdapter
import kotlinx.android.synthetic.main.activity_business.*


/**
 *
 *
 * @author chenchao
 * @date 3/2/21 14:37
 */
class BusinessActivity : BaseActivity(R.layout.activity_business) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BarUtils.transparentStatusBar(this)
        BarUtils.addMarginTopEqualStatusBarHeight(tv_header)

        iv_back.setOnClickListener { ActivityHelper.finish(BusinessActivity::class.java) }
        tv_refresh.setOnClickListener {
            showProgressDialog()
            tv_refresh.postDelayed({ dismissProgressDialog() }, 2000)
        }

        recyclerview.adapter = BusinessGroupAdapter(
            arrayListOf(
                BusinessGroupBean(getString(R.string.buss_num), "349065325670984678"),
                BusinessGroupBean(getString(R.string.buss_money), "2000"),
                BusinessGroupBean(getString(R.string.buss_actual_money), "1800"),
                BusinessGroupBean(getString(R.string.buss_limit_time), "7天"),
                BusinessGroupBean(getString(R.string.buss_rate), "10%"),
                BusinessGroupBean(getString(R.string.buss_day_rate), "10%"),
                BusinessGroupBean(getString(R.string.buss_time), TimeUtils.getNowString())
            )
        )
    }
}