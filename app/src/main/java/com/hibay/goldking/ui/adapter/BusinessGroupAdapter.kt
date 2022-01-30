package com.hibay.goldking.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hibay.goldking.R
import com.hibay.goldking.bean.BusinessGroupBean


/**
 *
 *
 * @author chenchao
 * @date 3/1/21 16:16
 */
class BusinessGroupAdapter(
    data: ArrayList<BusinessGroupBean>?,
    layoutResId: Int = R.layout.item_business_group
) : BaseQuickAdapter<BusinessGroupBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: BusinessGroupBean) {
        holder.setText(R.id.tv_title, item.title)
        holder.setText(R.id.tv_content, item.content)
    }
}