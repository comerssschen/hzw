package com.hibay.goldking.ui.fragment

import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.PermissionUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseFragment
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.common.showToast
import com.hibay.goldking.ui.act.SaoMaActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_home_devices.view.*


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

    override fun layoutRes() = R.layout.fragment_home

    override fun onResume() {
        super.onResume()
        BarUtils.addMarginTopEqualStatusBarHeight(tvHeader)
    }

    override fun initView() {
        super.initView()
        tvScan.setOnClickListener {
            PermissionUtils.permission(PermissionConstants.CAMERA).callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: List<String>) {
                    ActivityHelper.startActivity(SaoMaActivity::class.java)
                }

                override fun onDenied(permissionsDeniedForever: List<String>, permissionsDenied: List<String>) {
                    showToast("请检查权限")
                }
            }).request()
        }
        recyclerviewDevices.adapter = object : BaseQuickAdapter<Int, BaseViewHolder>(
            R.layout.item_home_devices,
            mutableListOf(0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0)
        ) {
            override fun convert(holder: BaseViewHolder, item: Int) {
                if (holder.layoutPosition % 2 == 0) {
                    holder.itemView.itemGroup.setBackgroundResource(R.color.white)
                } else {
                    holder.itemView.itemGroup.setBackgroundResource(R.color.color_F8F8F8)
                }
                if (item == 0) {
                    holder.itemView.tvDeviceStatus.text = "维修中"
                    holder.itemView.tvDeviceStatus.setTextColor(resources.getColor(R.color.color_FF8E00))
                } else {
                    holder.itemView.tvDeviceStatus.setTextColor(resources.getColor(R.color.color_4692FF))
                    holder.itemView.tvDeviceStatus.text = "未维修"
                }

            }
        }
    }

}