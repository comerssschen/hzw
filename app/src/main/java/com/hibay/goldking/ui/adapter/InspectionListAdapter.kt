package com.hibay.goldking.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hibay.goldking.R
import com.hibay.goldking.bean.InspectionListBean

class InspectionListAdapter(
    data: ArrayList<InspectionListBean>?,
    layoutResId: Int = R.layout.item_inspection
) : BaseQuickAdapter<InspectionListBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: InspectionListBean) {
        holder.setText(R.id.tvDeviceNum, "设备数：${item.successnum}/${item.totalnum}")
        holder.setText(R.id.tvLocation, "位 置：${item.inspectionPerson}")
        holder.setText(R.id.tvTime, "上报时间：${item.inspectionTime}")
        holder.setText(R.id.tvStatus, item.status)
        holder.setText(R.id.tvLastPerson, "最后巡检人：${item.inspectionPerson}")
        holder.setGone(R.id.tvLastPerson, item.inspectionPerson.isNullOrEmpty())
        when (item.status) {
            "未完成" -> {
                holder.setBackgroundResource(R.id.tvStatus, R.drawable.inspection_nofinish)
            }
            "未巡检" -> {
                holder.setBackgroundResource(R.id.tvStatus, R.drawable.inspection_no)
            }
            "已完成" -> {
                holder.setBackgroundResource(R.id.tvStatus, R.drawable.inspection_finish)
            }
        }
    }
}