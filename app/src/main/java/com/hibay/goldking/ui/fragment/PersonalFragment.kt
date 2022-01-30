package com.hibay.goldking.ui.fragment

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseFragment
import com.hibay.goldking.bean.MineGroupBean
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.ui.act.BusinessActivity
import com.hibay.goldking.ui.act.LoginActivity
import com.hibay.goldking.ui.act.MainActivity
import com.hibay.goldking.ui.adapter.MineGroupAdapter
import kotlinx.android.synthetic.main.fragment_personal.*


/**
 *
 *
 * @author chenchao
 * @date 3/1/21 11:36
 */
class PersonalFragment : BaseFragment() {
    companion object {
        fun newInstance() = PersonalFragment()
    }

    override fun layoutRes() = R.layout.fragment_personal

    override fun onResume() {
        super.onResume()
        BarUtils.addMarginTopEqualStatusBarHeight(tv_header)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_login.setOnClickListener {
            ActivityHelper.startActivity(LoginActivity::class.java)
            ActivityHelper.finish(MainActivity::class.java)
        }

        MineGroupAdapter(
            arrayListOf(
                MineGroupBean(100, R.drawable.mine_item1, "บันทึกสินเชื่อ"),
                MineGroupBean(101, R.drawable.mine_item2, "บัญชีธนาคารของฉัน"),
                MineGroupBean(102, R.drawable.mine_item3, "เกี่ยวกับเรา"),
                MineGroupBean(103, R.drawable.mine_item4, "ปัญหาที่พบบ่อย"),
                MineGroupBean(104, R.drawable.mine_item5, "บริการลูกค้าออนไลน์"),
                MineGroupBean(105, R.drawable.mine_item6, "ออกจากระบบ")
            )
        ).let {
            recyclerview.adapter = it
            it.setOnItemClickListener { adapter, view, position ->
                ActivityHelper.startActivity(BusinessActivity::class.java)
            }
        }


    }
}