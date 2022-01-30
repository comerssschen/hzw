package com.hibay.goldking.ui.fragment

import com.blankj.utilcode.util.BarUtils
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseFragment
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.ui.act.BusinessActivity
import kotlinx.android.synthetic.main.fragment_hone.*


/**
 *
 *
 * @author chenchao
 * @date 3/1/21 11:36
 */
class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun layoutRes() = R.layout.fragment_hone

    override fun onResume() {
        super.onResume()
        BarUtils.addMarginTopEqualStatusBarHeight(tv_header)
        ll_group1.setOnClickListener { ActivityHelper.startActivity(BusinessActivity::class.java) }
        ll_group2.setOnClickListener { ActivityHelper.startActivity(BusinessActivity::class.java) }
        bt_confirm.setOnClickListener { ActivityHelper.startActivity(BusinessActivity::class.java) }
    }


}