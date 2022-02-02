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
        holder.setText(R.id.tvDeviceNum, item.endDate)
    }
}