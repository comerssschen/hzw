package com.hibay.goldking.ui.fragment

import android.os.Bundle
import androidx.core.view.isVisible
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseVmFragment
import com.hibay.goldking.bean.DotBean
import com.hibay.goldking.bean.InspectionListBean
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.common.BusHelper
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
        BusHelper.observe<Boolean>("InspectionFragmentUpdate", this) {
            initData()
        }
        mViewModel.inspectionList.observe(this) {
            arguments?.getInt(ChildReportFragment.TYPE)?.let { type ->
                BusHelper.post("Dot", DotBean(type, it.size))
            }

            mAAdapter.setNewInstance(it)
            llEmpty.isVisible = it.isEmpty()
            swipeRefreshLayout.isVisible = it.isNotEmpty()
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
            ActivityHelper.startActivity(InspectionDetailActivity::class.java, mapOf("groupInspectionId" to bean.groupInspectionId))
        }
        recyclerview.adapter = mAAdapter
    }
}