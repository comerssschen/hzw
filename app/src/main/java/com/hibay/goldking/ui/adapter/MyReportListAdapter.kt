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
        holder.setVisible(R.id.ivReportFail, item.statusCode == 6)
        holder.setText(R.id.tvStatus, item.status)
//        状态编码（“1”代表未接单，“2”代表维修中，”3”是查试运行的，“4”代表试运行，“5”代表已结束）
        when (item.statusCode) {
            1 -> {
                holder.setBackgroundResource(R.id.tvStatus, R.drawable.inspection_no)
            }
            2 -> {
                holder.setBackgroundResource(R.id.tvStatus, R.drawable.inspection_nofinish)
            }
            3 -> {
                holder.setBackgroundResource(R.id.tvStatus, R.drawable.inspection_no)
            }
            4 -> {
                holder.setBackgroundResource(R.id.tvStatus, R.drawable.inspection_finish)
            }
            5 -> {
                holder.setBackgroundResource(R.id.tvStatus, R.drawable.inspection_finish)
            }
        }
    }
}