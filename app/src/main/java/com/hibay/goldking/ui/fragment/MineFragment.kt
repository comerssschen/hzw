package com.hibay.goldking.ui.fragment

import com.blankj.utilcode.util.BarUtils
import com.google.gson.Gson
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseVmFragment
import com.hibay.goldking.bean.UserInfo
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.common.userInfo
import com.hibay.goldking.ui.act.PersonalActivity
import com.hibay.goldking.ui.act.MyReportActivity
import com.hibay.goldking.ui.viewmodel.MineFragmentViewModel
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseVmFragment<MineFragmentViewModel>() {
    override fun viewModelClass() = MineFragmentViewModel::class.java

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun layoutRes() = R.layout.fragment_mine

    override fun onResume() {
        super.onResume()
        BarUtils.addMarginTopEqualStatusBarHeight(ivLogo)
        mViewModel.getAppWorkOrderInfo()
    }

    override fun observe() {
        super.observe()
        mViewModel.infoResult.observe(this) {
            tvAllOrder.text = it.allNum
            tvSucces.text = it.finishNum
            tvWait.text = it.waitNum
        }
    }

    override fun initView() {
        super.initView()
        Gson().fromJson(userInfo, UserInfo::class.java).let {
            tvName.text = it.name
        }
        tvMyReport.setOnClickListener { toMyOrderAct() }
        clOrder.setOnClickListener { toMyOrderAct() }
        ivTopBg.setOnClickListener {
            ActivityHelper.startActivity(PersonalActivity::class.java)
        }
    }

    private fun toMyOrderAct() {
        ActivityHelper.startActivity(MyReportActivity::class.java)
    }

}