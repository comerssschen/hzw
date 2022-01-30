package com.hibay.goldking.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hibay.goldking.R
import com.hibay.goldking.bean.MineGroupBean


/**
 *
 *
 * @author chenchao
 * @date 3/1/21 16:16
 */
class MineGroupAdapter(
    data: ArrayList<MineGroupBean>?,
    layoutResId: Int = R.layout.item_mine_group
) : BaseQuickAdapter<MineGroupBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: MineGroupBean) {
        holder.setText(R.id.tv_content, item.text)
            .setImageResource(R.id.iv_icon, item.image)
    }
}