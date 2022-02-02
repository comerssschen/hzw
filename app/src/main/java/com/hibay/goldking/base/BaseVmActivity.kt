package com.hibay.goldking.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.ui.act.LoginActivity
import com.hibay.goldking.ui.act.MainActivity


abstract class BaseVmActivity<VM : BaseViewModel>(res: Int) : BaseActivity(res) {

    protected open lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        observe()
        initData()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    protected abstract fun viewModelClass(): Class<VM>

    open fun observe() {
        mViewModel.loginStatusInvalid.observe(this) {
            if (it) {
                ActivityHelper.startActivity(LoginActivity::class.java)
                ActivityHelper.finishAll(LoginActivity::class.java)
            }
        }
        mViewModel.showLoadding.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                dismissProgressDialog()
            }
        }
    }

    open fun initData() {}

}