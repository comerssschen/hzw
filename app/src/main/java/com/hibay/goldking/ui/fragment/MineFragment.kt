package com.hibay.goldking.ui.fragment

import com.hibay.goldking.R
import com.hibay.goldking.base.BaseFragment
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.common.loginOut
import com.hibay.goldking.ui.act.LoginActivity
import com.hibay.goldking.ui.act.MainActivity
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseFragment() {

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun layoutRes() = R.layout.fragment_mine

    override fun initView() {
        super.initView()
        btLoginOut.setOnClickListener {
            loginOut()
            ActivityHelper.startActivity(LoginActivity::class.java)
            ActivityHelper.finish(MainActivity::class.java)
        }
    }

}