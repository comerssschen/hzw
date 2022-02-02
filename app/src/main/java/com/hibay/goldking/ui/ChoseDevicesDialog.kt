package com.hibay.goldking.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.GridLayoutManager
import com.hibay.goldking.R
import com.hibay.goldking.bean.FacilityInfoListBean
import com.hibay.goldking.ui.adapter.ChoseDeviceAdapter
import kotlinx.android.synthetic.main.dialog_chosedevice_fragment.*

class ChoseDevicesDialog(mContext: Context, bean: FacilityInfoListBean, currentNum: Int, choseItem: (Int) -> Unit) : Dialog(mContext) {
    var mAdapter: ChoseDeviceAdapter? = null
    var mCurrentNum = 0

    init {
        setContentView(R.layout.dialog_chosedevice_fragment)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lp = window?.attributes
        val display = (mContext as Activity).windowManager.defaultDisplay
        lp?.width = (display.width)
        window?.attributes = lp
        dialog_recyclerview.layoutManager = GridLayoutManager(context, 6)
        mAdapter = ChoseDeviceAdapter(bean.reList)
        dialog_recyclerview.adapter = mAdapter
        mCurrentNum = currentNum
        mAdapter?.setCurrentPosition(currentNum)
        mAdapter?.setOnItemClickListener { _, _, position ->
            if (position == mCurrentNum) return@setOnItemClickListener
            mCurrentNum = currentNum
            mAdapter?.setCurrentPosition(position)
            choseItem.invoke(position)
            dismiss()
        }
        refreshUI(bean, currentNum)
    }

    fun refreshUI(bean: FacilityInfoListBean?, currentNum: Int) {
        mCurrentNum = currentNum
        tvSuccesNum.text = bean?.goodNum
        tvFailNum.text = bean?.badNum
        tvTotal.text = "" + (currentNum + 1) + "/" + bean?.sum
        mAdapter?.setList(bean?.reList)
        mAdapter?.setCurrentPosition(currentNum)
    }

    override fun show() {
        super.show()
        if (!isShowing) {
            show()
        }
    }
}