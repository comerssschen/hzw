package com.hibay.goldking.ui.fragment

import android.os.Bundle
import androidx.core.view.isVisible
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseVmFragment
import com.hibay.goldking.bean.ReportListBean
import com.hibay.goldking.ui.act.MyReportActivity
import com.hibay.goldking.ui.adapter.MyReportListAdapter
import com.hibay.goldking.ui.viewmodel.MyReportViewModel
import kotlinx.android.synthetic.main.fragment_child_inspection.*

class ChildReportFragment : BaseVmFragment<MyReportViewModel>() {
    override fun viewModelClass() = MyReportViewModel::class.java
    override fun layoutRes() = R.layout.fragment_child_inspection

    companion object {
        const val TYPE = "type"
        fun newInstance(type: Int): ChildReportFragment {
            val fragment = ChildReportFragment()
            val bundle = Bundle()
            bundle.putInt(TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.getReportList(arguments?.getInt(TYPE))
    }

    override fun observe() {
        super.observe()
        mViewModel.inspectionList.observe(this) {
            arguments?.getInt(TYPE).let { type ->
                (activity as MyReportActivity).setDot(type, it.size)
            }

            mAAdapter.setNewInstance(it)
            llEmpty.isVisible = it.isEmpty()
            swipeRefreshLayout.isVisible = it.isNotEmpty()
        }
        mViewModel.refreshStatus.observe(this) {
            swipeRefreshLayout.isRefreshing = it
        }
    }

    lateinit var mAAdapter: MyReportListAdapter
    override fun initView() {
        super.initView()
        swipeRefreshLayout.setOnRefreshListener {
            mViewModel.getReportList(arguments?.getInt(TYPE))
        }
        mAAdapter = MyReportListAdapter(null)
        mAAdapter.setOnItemClickListener { adapter, _, position ->
            val bean = adapter.data[position] as ReportListBean
        }
        recyclerview.adapter = mAAdapter
    }
}