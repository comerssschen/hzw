package com.hibay.goldking.ui.fragment

import com.blankj.utilcode.util.BarUtils
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseFragment
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
        BarUtils.addMarginTopEqualStatusBarHeight(tvTitle)
    }

    override fun initView() {
        super.initView()
        tvTitle.text = "巡检列表"
        viewpager.adapter = SimpleFragmentPagerAdapter(
            childFragmentManager,
            fragments = listOf(
                ChildInspectionFragment.newInstance(0),
                ChildInspectionFragment.newInstance(1),
                ChildInspectionFragment.newInstance(2),
                ChildInspectionFragment.newInstance(3),
            ),
            listOf(
                "全部", "未巡检", "未完成", "已完成"
            )
        )
        viewpager.offscreenPageLimit = 3
        tablayout.setupWithViewPager(viewpager, false)

    }

}