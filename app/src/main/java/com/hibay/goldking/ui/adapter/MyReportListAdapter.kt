package com.hibay.goldking.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hibay.goldking.R
import com.hibay.goldking.bean.ReportListBean

class MyReportListAdapter(
    data: ArrayList<ReportListBean>?,
    layoutResId: Int = R.layout.item_myreport
) : BaseQuickAdapter<ReportListBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: ReportListBean) {
        holder.setText(R.id.tvTime, item.createTime)
        holder.setText(R.id.tvLocation, item.location)
        holder.setText(R.id.tvDes, item.detail)
        holder.setText(R.id.tvDetail, item.reason)
        holder.setText(R.id.tvStatus, item.status)
//        “0”是查所有的,”1”是查未接单的,”2”是查维修中的,”3”是查试运行的，,”4”是查已完成的
        when (item.status) {
            "0" -> {
                holder.setBackgroundResource(R.id.tvStatus, R.drawable.inspection_finish)
            }
            "未接单" -> {
                holder.setBackgroundResource(R.id.tvStatus, R.drawable.inspection_no)
            }
            "维修中" -> {
                holder.setBackgroundResource(R.id.tvStatus, R.drawable.inspection_nofinish)
            }
            "审核中" -> {
                holder.setBackgroundResource(R.id.tvStatus, R.drawable.inspection_no)
            }
            "已结束" -> {
                holder.setBackgroundResource(R.id.tvStatus, R.drawable.inspection_finish)
            }
        }
    }
}