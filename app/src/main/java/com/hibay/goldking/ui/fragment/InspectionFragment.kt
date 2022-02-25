package com.hibay.goldking.ui.fragment

import androidx.core.view.isVisible
import com.blankj.utilcode.util.BarUtils
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseFragment
import com.hibay.goldking.bean.DotBean
import com.hibay.goldking.common.BusHelper
import com.hibay.goldking.common.SimpleFragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_inspection.*
import kotlinx.android.synthetic.main.mytoolbar.*

class InspectionFragment : BaseFragment() {
    companion object {
        fun newInstance() = InspectionFragment()
    }

    override fun layoutRes() = R.layout.fragment_inspection

    override fun onResume() {
        super.onResume()
        BusHelper.post("InspectionFragmentUpdate", true)
        BarUtils.addMarginTopEqualStatusBarHeight(tvTitle)
    }

    override fun initView() {
        super.initView()
        ivBack.isVisible = false
        tvTitle.text = "巡检列表"
        viewpager.offscreenPageLimit = 4
        viewpager.adapter = SimpleFragmentPagerAdapter(
            childFragmentManager, listOf(
                ChildInspectionFragment.newInstance(0),
                ChildInspectionFragment.newInstance(1),
                ChildInspectionFragment.newInstance(2),
                ChildInspectionFragment.newInstance(3),
            ), listOf("全部", "未巡检", "未完成", "已完成")
        )
        tablayout.setViewPager(viewpager)
        BusHelper.observe<DotBean>("Dot", this) {
            setDot(it.position, it.count)
        }
    }

    fun setDot(type: Int?, mCount: Int) {
        if (type == 0 || type == 3) return
        if (mCount == 0) {
            tablayout.hideMsg(type!!)
        } else {
            tablayout.showMsg(type!!, mCount)
        }
        tablayout.setMsgMargin(type, 20f, 12f)
    }
}