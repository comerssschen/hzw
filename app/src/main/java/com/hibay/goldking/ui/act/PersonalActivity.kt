package com.hibay.goldking.ui.act

import com.blankj.utilcode.util.BarUtils
import com.google.gson.Gson
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseVmActivity
import com.hibay.goldking.bean.UserInfo
import com.hibay.goldking.common.*
import com.hibay.goldking.ui.view.CommonDialog
import com.hibay.goldking.ui.viewmodel.PersonalViewModel
import kotlinx.android.synthetic.main.activity_personal.*
import kotlinx.android.synthetic.main.mytoolbar.*

class PersonalActivity : BaseVmActivity<PersonalViewModel>(R.layout.activity_personal) {
    override fun viewModelClass() = PersonalViewModel::class.java
    override fun initView() {
        super.initView()
        Gson().fromJson(userInfo, UserInfo::class.java).let {
            tvName.text = it.name
            tvPhone.text = it.phone
            tvCompany.text = it.company
            tvLevel.text = it.position
        }
        BarUtils.transparentStatusBar(this)
        BarUtils.addMarginTopEqualStatusBarHeight(tvTitle)
        tvTitle.text = "个人信息"
        ivBack.setOnClickListener { ActivityHelper.finish(PersonalActivity::class.java) }
        tvLoginOut.setOnClickListener {
            CommonDialog(this, "提示", "确认退出登录吗？", {
                loginOut()
                ActivityHelper.startActivity(LoginActivity::class.java)
                ActivityHelper.finish(PersonalActivity::class.java)
                ActivityHelper.finish(MainActivity::class.java)
            }, {}).show()
        }

//        tvPhone.setOnClickListener { ActivityHelper.startActivity(EditPhoneActivity::class.java, mapOf("phone" to accountName)) }
//        tvCompany.setOnClickListener { ActivityHelper.startActivity(EditPhoneActivity::class.java) }
//        tvLevel.setOnClickListener { ActivityHelper.startActivity(EditPhoneActivity::class.java) }
    }
}