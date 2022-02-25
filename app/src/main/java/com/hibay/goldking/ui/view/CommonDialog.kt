package com.hibay.goldking.ui.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.hibay.goldking.R
import kotlinx.android.synthetic.main.dialog_common.*

class CommonDialog(mContext: Context, title: String, msg: String, confirm: () -> Unit, cancle: () -> Unit) : Dialog(mContext) {
    init {
        setContentView(R.layout.dialog_common)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lp = window?.attributes
        val display = (mContext as Activity).windowManager.defaultDisplay
        lp?.width = (display.width * 0.7).toInt() // 宽度
        window?.attributes = lp
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        tvTitle.text = title
        tvContent.text = msg
        tvConfirm.setOnClickListener {
            confirm.invoke()
            dismiss()
        }
        tvCancle.setOnClickListener {
            cancle.invoke()
            dismiss()
        }
    }

    override fun show() {
        super.show()
        if (!isShowing) {
            show()
        }
    }

}