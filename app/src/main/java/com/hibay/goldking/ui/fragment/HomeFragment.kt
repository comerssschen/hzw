package com.hibay.goldking.ui.fragment

import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.PermissionUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseVmFragment
import com.hibay.goldking.bean.ErrorFacilityList
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.common.SpaceItemDecoration
import com.hibay.goldking.common.dp2px
import com.hibay.goldking.common.showToast
import com.hibay.goldking.ui.act.SaoMaActivity
import com.hibay.goldking.ui.viewmodel.ErrorFacilityViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_home_devices.view.*


/**
 *
 *
 * @author chenchao
 * @date 3/1/21 11:36
 */
class HomeFragment : BaseVmFragment<ErrorFacilityViewModel>() {
    override fun viewModelClass() = ErrorFacilityViewModel::class.java

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun layoutRes() = R.layout.fragment_home

    override fun onResume() {
        super.onResume()
        BarUtils.addMarginTopEqualStatusBarHeight(tvHeader)
    }

    var mAdapter: BaseQuickAdapter<ErrorFacilityList, BaseViewHolder>? = null
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
        mAdapter = object : BaseQuickAdapter<ErrorFacilityList, BaseViewHolder>(R.layout.item_home_devices, null) {
            override fun convert(holder: BaseViewHolder, item: ErrorFacilityList) {
                holder.itemView.run {
                    tvDeviceName.text = item.model ?: "-"
                    tvLocation.text = item.location ?: "-"
                    tvTime.text = item.createTime ?: "-"
                    tvStatus.text = item.status ?: "-"
                    if (item.statusCode == "0") {
                        tvStatus.setBackgroundResource(R.drawable.device_no_deal)
                    } else {
                        tvStatus.setBackgroundResource(R.drawable.device_dealing)
                    }

                    Glide.with(context).load(item.picture)
                        .transform(RoundedCorners(ConvertUtils.dp2px(5f)))
                        .apply(RequestOptions().fallback(R.drawable.device_fail_default).error(R.drawable.device_fail_default))
                        .into(ivLogo)
                }
            }
        }
        recyclerviewDevices.addItemDecoration(SpaceItemDecoration(dp2px(1f)))
        recyclerviewDevices.adapter = mAdapter
        swipeRefreshLayout.setOnRefreshListener {
            initData()
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.getErrorFacilityList()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            errorFacilityListReslt.observe(this@HomeFragment) {
                mAdapter?.setNewInstance(it)
            }
            refreshStatus.observe(this@HomeFragment) {
                swipeRefreshLayout.isRefreshing = it
            }
        }
    }
}