package com.hibay.goldking.ui.fragment

import android.os.Bundle
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseVmFragment
import com.hibay.goldking.bean.InspectionListBean
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.ui.act.InspectionDetailActivity
import com.hibay.goldking.ui.adapter.InspectionListAdapter
import com.hibay.goldking.ui.viewmodel.InspectionViewMoel
import kotlinx.android.synthetic.main.fragment_child_inspection.*

class ChildInspectionFragment : BaseVmFragment<InspectionViewMoel>() {
    override fun viewModelClass() = InspectionViewMoel::class.java
    override fun layoutRes() = R.layout.fragment_child_inspection

    companion object {
        const val TYPE = "type"
        fun newInstance(type: Int): ChildInspectionFragment {
            val fragment = ChildInspectionFragment()
            val bundle = Bundle()
            bundle.putInt(TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.getInspectionList(arguments?.getInt(TYPE))
    }

    override fun observe() {
        super.observe()
        mViewModel.inspectionList.observe(this) {
            mAAdapter.setNewInstance(it)
        }
        mViewModel.refreshStatus.observe(this) {
            swipeRefreshLayout.isRefreshing = it
        }
    }

    lateinit var mAAdapter: InspectionListAdapter
    override fun initView() {
        super.initView()
        swipeRefreshLayout.setOnRefreshListener {
            mViewModel.getInspectionList(arguments?.getInt(TYPE))
        }
        mAAdapter = InspectionListAdapter(null)
        mAAdapter.setOnItemClickListener { adapter, _, position ->
            val bean = adapter.data[position] as InspectionListBean
            ActivityHelper.startActivity(InspectionDetailActivity::class.java, mapOf("id" to bean.groupInspectionId))
        }
        recyclerview.adapter = mAAdapter
    }
}