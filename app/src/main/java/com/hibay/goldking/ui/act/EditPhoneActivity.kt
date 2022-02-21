package com.hibay.goldking.ui.act

import android.graphics.Color
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.blankj.utilcode.util.BarUtils
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseVmActivity
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.common.showToast
import com.hibay.goldking.common.toStr
import com.hibay.goldking.ui.viewmodel.PersonalViewModel
import kotlinx.android.synthetic.main.activity_edit_phone.*

class EditPhoneActivity : BaseVmActivity<PersonalViewModel>(R.layout.activity_edit_phone) {
    override fun viewModelClass() = PersonalViewModel::class.java
    override fun initView() {
        super.initView()
        BarUtils.addMarginTopEqualStatusBarHeight(tvHeader)
        BarUtils.setStatusBarColor(this, Color.parseColor("#F3F3F3"))
        etPhone.setText(intent.getStringExtra("phone"))
        tvBack.setOnClickListener { ActivityHelper.finish(EditPhoneActivity::class.java) }
        ivClear.setOnClickListener { etPhone.text.clear() }
        etPhone.addTextChangedListener { ivClear.isVisible = etPhone.text.isNotEmpty() }
        btConfirm.setOnClickListener {
            if (etPhone.toStr().isEmpty()) {
                showToast("请输入")
            } else {
                mViewModel
            }

        }
    }
}