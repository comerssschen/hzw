package com.hibay.goldking.ui.fragment

import com.hibay.goldking.R
import com.hibay.goldking.base.BaseFragment

class DevicesFragment : BaseFragment() {
    companion object {
        fun newInstance() = DevicesFragment()
    }

    override fun layoutRes() = R.layout.fragment_devices
}