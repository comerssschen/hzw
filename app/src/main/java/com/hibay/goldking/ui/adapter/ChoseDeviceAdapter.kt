package com.hibay.goldking.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hibay.goldking.R
import com.hibay.goldking.bean.ReList

class ChoseDeviceAdapter(
    data: ArrayList<ReList>?,
    layoutResId: Int = R.layout.item_chose_device
) : BaseQuickAdapter<ReList, BaseViewHolder>(layoutResId, data) {
    private var currentPosition: Int = 0
    override fun convert(holder: BaseViewHolder, item: ReList) {
        holder.setText(R.id.tvDeviceNum, (getItemPosition(item) + 1).toString())
        if (currentPosition == getItemPosition(item)) {
            when (item.statusCode) {
                "1" -> {
                    holder.setBackgroundResource(R.id.tvDeviceNum, R.drawable.device_status_success_select)
                }
                "2" -> {
                    holder.setBackgroundResource(R.id.tvDeviceNum, R.drawable.device_status_fail_select)
                }
                else -> {
                    holder.setBackgroundResource(R.id.tvDeviceNum, R.drawable.device_undeal_select)
                }
            }

        } else {
            when (item.statusCode) {
                "1" -> {
                    holder.setBackgroundResource(R.id.tvDeviceNum, R.drawable.device_status_success)
                }
                "2" -> {
                    holder.setBackgroundResource(R.id.tvDeviceNum, R.drawable.device_status_fail)
                }
                else -> {
                    holder.setBackgroundResource(R.id.tvDeviceNum, R.drawable.device_undeal)
                }
            }
        }
    }

    fun setCurrentPosition(position: Int) {
        currentPosition = position
        notifyDataSetChanged()
    }

}