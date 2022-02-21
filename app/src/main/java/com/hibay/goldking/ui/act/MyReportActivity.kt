package com.hibay.goldking.ui.act

import com.blankj.utilcode.util.BarUtils
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseVmActivity
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.common.SimpleFragmentPagerAdapter
import com.hibay.goldking.ui.fragment.ChildReportFragment
import com.hibay.goldking.ui.viewmodel.MyReportViewModel
import kotlinx.android.synthetic.main.activity_myreport.*
import kotlinx.android.synthetic.main.mytoolbar.*

class MyReportActivity : BaseVmActivity<MyReportViewModel>(R.layout.activity_myreport) {
    override fun viewModelClass() = MyReportViewModel::class.java
    val titleList = listOf("全部", "未接单", "维修中", "试运行", "已结束")

    override fun initView() {
        super.initView()
        BarUtils.transparentStatusBar(this)
        BarUtils.addMarginTopEqualStatusBarHeight(tvTitle)
        tvTitle.text = "我的上报"
        ivBack.setOnClickListener { ActivityHelper.finish(MyReportActivity::class.java) }
        viewpager.offscreenPageLimit = 5
        viewpager.adapter = SimpleFragmentPagerAdapter(
            supportFragmentManager, listOf(
                ChildReportFragment.newInstance(0),
                ChildReportFragment.newInstance(1),
                ChildReportFragment.newInstance(2),
                ChildReportFragment.newInstance(3),
                ChildReportFragment.newInstance(4),
            ), titleList
        )
        tablayout.setViewPager(viewpager)
    }

    fun setDot(type: Int?, mCount: Int) {
        if (type == 0 || type == 4) return
        if (mCount == 0) {
            tablayout.hideMsg(type!!)
        } else {
            tablayout.showMsg(type!!, mCount)
        }
        tablayout.setMsgMargin(type, 12f, 12f)
    }
}