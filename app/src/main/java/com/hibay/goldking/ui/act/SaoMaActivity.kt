package com.hibay.goldking.ui.act

import cn.bingoogolapple.qrcode.core.BarcodeType
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.blankj.utilcode.util.BarUtils
import com.google.gson.Gson
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseVmActivity
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.ui.viewmodel.InspectionViewMoel
import kotlinx.android.synthetic.main.activity_saoma.*

class SaoMaActivity : BaseVmActivity<InspectionViewMoel>(R.layout.activity_saoma), QRCodeView.Delegate {
    override fun viewModelClass() = InspectionViewMoel::class.java
    override fun initView() {
        super.initView()
        BarUtils.transparentStatusBar(this)
        BarUtils.addMarginTopEqualStatusBarHeight(ivBack)
        zxingview.setDelegate(this)
        zxingview.changeToScanQRCodeStyle() // 切换成扫描二维码样式
        zxingview.setType(BarcodeType.HIGH_FREQUENCY, null) // 识别所有类型的码
        zxingview.startSpotAndShowRect()// 显示扫描框，并开始识别
        ivBack.setOnClickListener { ActivityHelper.finish(SaoMaActivity::class.java) }
    }

    override fun onScanQRCodeSuccess(result: String) {
        mViewModel.getFacilityInfoList(result)
    }

    override fun observe() {
        super.observe()
        mViewModel.facilityInfoListResult.observe(this) {
            ActivityHelper.startActivity(InspectionDetailActivity::class.java, mapOf("facilityInfoListResult" to it))
            ActivityHelper.finish(SaoMaActivity::class.java)
        }
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {}

    override fun onScanQRCodeOpenCameraError() {}
    override fun onStart() {
        super.onStart()
        zxingview.startCamera() // 打开后置摄像头开始预览，但是并未开始识别
        zxingview.startSpotAndShowRect() // 显示扫描框，并开始识别
    }

    override fun onStop() {
        zxingview.stopCamera() // 关闭摄像头预览，并且隐藏扫描框
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        zxingview.onDestroy()
    }
}