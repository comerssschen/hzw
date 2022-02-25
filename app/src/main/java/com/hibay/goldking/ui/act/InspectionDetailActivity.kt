package com.hibay.goldking.ui.act

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.PermissionUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hibay.goldking.R
import com.hibay.goldking.base.BaseVmActivity
import com.hibay.goldking.bean.FacilityInfoListBean
import com.hibay.goldking.bean.ReList
import com.hibay.goldking.common.ActivityHelper
import com.hibay.goldking.common.GlideEngine
import com.hibay.goldking.common.showToast
import com.hibay.goldking.common.toArrayList
import com.hibay.goldking.ui.ChoseDevicesDialog
import com.hibay.goldking.ui.view.CommonDialog
import com.hibay.goldking.ui.viewmodel.InspectionViewMoel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.activity_inspection.*
import kotlinx.android.synthetic.main.mytoolbar.*
import java.io.File
import kotlin.math.abs

class InspectionDetailActivity : BaseVmActivity<InspectionViewMoel>(R.layout.activity_inspection) {
    override fun viewModelClass() = InspectionViewMoel::class.java
    var mAdapter: BaseQuickAdapter<String, BaseViewHolder>? = null
    private var values = ArrayList<String>()
    private var mPosition = 0
    private var isAdd = true
    val REQUESTCODE1 = 30
    override fun initView() {
        super.initView()
        BarUtils.transparentStatusBar(this)
        BarUtils.addMarginTopEqualStatusBarHeight(tvTitle)
        ivBack.setOnClickListener { ActivityHelper.finish(InspectionDetailActivity::class.java) }
        ivPhoto.setOnClickListener {
            isAdd = true
            PictureSelector.create(this@InspectionDetailActivity)
                .openGallery(PictureMimeType.ofImage())//相册和拍照
                .selectionMode(PictureConfig.SINGLE)
                .imageEngine(GlideEngine.createGlideEngine())
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .isCompress(true) //压缩
                .cutOutQuality(50) //压缩后图片质量
                .forResult(REQUESTCODE1)
        }

        llLeft.setOnClickListener {
            pointLeft()
        }
        llRight.setOnClickListener {
            pointRight()
        }
        clDialog.setOnClickListener {
            mList?.let {
                showBottomDialog()
            }
        }
    }

    var loandialog: ChoseDevicesDialog? = null
    private fun showBottomDialog() {
        if (loandialog == null) {
            loandialog = ChoseDevicesDialog(this, mList!!, currentNum) {
                currentNum = it
                notifySelcet()
            }
            loandialog?.window?.setGravity(Gravity.BOTTOM)
        }
        loandialog?.show()
    }

    private fun initRecycler() {
        mAdapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_photo) {
            override fun convert(holder: BaseViewHolder, item: String) {
                Glide.with(context).load(item).transform(RoundedCorners(ConvertUtils.dp2px(5f)))
                    .into(holder.getView<View>(R.id.ivImage) as ImageView)
                (holder.getView<View>(R.id.ivDeletePhoto) as ImageView).isVisible = canEdit
            }
        }
        if (canEdit) {
            mAdapter?.setOnItemClickListener { adapter, view, position ->
                PermissionUtils.permissionGroup(PermissionConstants.CAMERA, PermissionConstants.STORAGE)
                    .callback(object : PermissionUtils.SimpleCallback {
                        override fun onGranted() {
                            isAdd = false
                            mPosition = position
                            PictureSelector.create(this@InspectionDetailActivity)
                                .openGallery(PictureMimeType.ofImage())//相册和拍照
                                .selectionMode(PictureConfig.SINGLE)
                                .imageEngine(GlideEngine.createGlideEngine())
                                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                .isCompress(true) //压缩
                                .cutOutQuality(50) //压缩后图片质量
                                .forResult(REQUESTCODE1)
                        }

                        override fun onDenied() {
                            showToast("Insufficient authority")
                        }
                    }).request()

            }
            mAdapter?.addChildClickViewIds(R.id.ivDeletePhoto)
            mAdapter?.setOnItemChildClickListener { _, _, position ->
                values.removeAt(position)
                mAdapter?.setList(values)
                if (values.size >= 3) {
                    ivPhoto.visibility = View.INVISIBLE
                } else {
                    ivPhoto.visibility = View.VISIBLE
                }
            }
        }
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerview.layoutManager = linearLayoutManager
        recyclerview.adapter = mAdapter
    }

    var hasNext = false
    private fun pointNext() {
        hasNext = false
        run {
            mList?.reList?.forEachIndexed { index, item ->
                if (item.statusCode.isNullOrEmpty() && index >= currentNum) {
                    hasNext = true
                    currentNum = index
                    return@run
                }
            }
        }
        notifySelcet()
        if (!hasNext && canEdit) {
            if (mList!!.reList!!.size == mList!!.completeNum) {
                CommonDialog(this, "巡检完成", "已完成${(mList!!.reList!!.size)}个设备的巡检 点击确认完成任务", {
                    ActivityHelper.finish(InspectionDetailActivity::class.java)
                }, {}).show()
            } else {
                CommonDialog(this, "巡检未完成", "已完成${mList!!.completeNum}个设备的巡检\n未完成${(mList!!.reList!!.size - mList!!.completeNum!!)}个设备的巡检\n点击确认结束巡检", {
                    ActivityHelper.finish(InspectionDetailActivity::class.java)
                }, {}).show()
            }
        }
    }

    private fun pointRight() {
        if (currentNum < mList!!.reList!!.size - 1) {
            currentNum += 1
            notifySelcet()
        }
    }

    private fun pointLeft() {
        if (currentNum > 0) {
            currentNum -= 1
            notifySelcet()
        }
    }

    private fun notifySelcet() {
        initeCurrentDevice(mList!!.reList!![currentNum])
        tvCurrentNum.text = "" + (currentNum + 1) + "/" + mList?.sum
        tvSuccesNum.text = mList?.goodNum
        tvFailNum.text = mList?.badNum

        if (loandialog != null) {
            loandialog?.refreshUI(mList, currentNum)
        }
    }

    var mList: FacilityInfoListBean? = null
    var currentNum = 0
    var canEdit = false
    override fun initData() {
        super.initData()
        intent.getStringExtra("groupInspectionId")?.let {
            canEdit = false
            mViewModel.queryFacilityInfoByGroupInspectionId(it)
        }
        intent.getParcelableExtra<FacilityInfoListBean>("facilityInfoListResult")?.let {
            canEdit = true
            mList = it
            setViewStatas()
        }

        tvCancle.setOnClickListener {
            group.isVisible = false
        }
        tvConfirm.setOnClickListener {
            if (values.size > 0) {
                mViewModel.updateFacilityStatus(mList!!.reList!![currentNum], "2", values.joinToString { i -> i }, etFailMessage.text.toString(), swMaintain.isChecked)
            } else {
                showToast("请上传图片")
            }
        }
        tvSucces.setOnClickListener {
            group.isVisible = false
            mViewModel.updateFacilityStatus(mList!!.reList!![currentNum], "1")
        }
        tvFail.setOnClickListener {
            group.isVisible = true
        }

    }

    private fun setViewStatas() {
        currentNum = 0
        initRecycler()
        notifySelcet()
        tvSucces.isEnabled = canEdit
        tvFail.isEnabled = canEdit

        etFailMessage.isEnabled = canEdit
        swMaintain.isEnabled = canEdit
        if (canEdit) {
            mList?.let {
                showBottomDialog()
            }
            tvTitle.text = "开始巡检"
        } else {
            tvTitle.text = "巡检详情"
        }
    }

    private fun initeCurrentDevice(currentBeam: ReList?) {
        if (currentBeam == null) return
        when (currentBeam.statusCode) {
            "1" -> {
                group.isVisible = false
                values.clear()
                mAdapter?.setList(values)
                etFailMessage.setText("")
                swMaintain.isChecked = false
            }
            "2" -> {
                group.isVisible = true
                tvCancle.isVisible = canEdit
                tvConfirm.isVisible = canEdit
                ivPhoto.isVisible = canEdit
                etFailMessage.setText(currentBeam.faultDesc)
                swMaintain.isChecked = currentBeam.maintain == "1"
                values.clear()
                values = currentBeam.imageAddress?.split(",")?.toArrayList() ?: arrayListOf()
                mAdapter?.setNewInstance(values)
            }
            else -> {
                group.isVisible = false
                values.clear()
                mAdapter?.setList(values)
                etFailMessage.setText("")
                swMaintain.isChecked = false
            }
        }

        Glide.with(this).load(currentBeam.imageAddress).apply(
            RequestOptions().fallback(R.drawable.inject_default)
                .error(R.drawable.inject_default)
                .transform(RoundedCorners(ConvertUtils.dp2px(5f)))
        ).into(ivDevicesLogo)
        tvPosition.text = if (currentNum + 1 < 10) {
            "0" + (currentNum + 1)
        } else {
            (currentNum + 1).toString()
        }
        tvDeviceName.text = currentBeam.name
        tvDeviceID.text = currentBeam.facilityId
        tvLocation.text = currentBeam.location

        when (currentBeam.statusCode) {
            "1" -> {
                tvSucces.setTextColor(Color.parseColor("#ffffff"))
                tvSucces.setBackgroundResource(R.drawable.common_green_bg)
                tvFail.setTextColor(Color.parseColor("#FF5F00"))
                tvFail.setBackgroundResource(R.drawable.fail_uninspection)
            }
            "2" -> {
                tvSucces.setTextColor(Color.parseColor("#68D279"))
                tvSucces.setBackgroundResource(R.drawable.normal_uninspection)
                tvFail.setTextColor(Color.parseColor("#ffffff"))
                tvFail.setBackgroundResource(R.drawable.common_select_bg)
            }
            else -> {
                tvSucces.setTextColor(Color.parseColor("#68D279"))
                tvSucces.setBackgroundResource(R.drawable.normal_uninspection)
                tvFail.setTextColor(Color.parseColor("#FF5F00"))
                tvFail.setBackgroundResource(R.drawable.fail_uninspection)
            }
        }
        tvRecentMaintain.isVisible = currentBeam.recentMaintain == true
        tvRecentInstall.isVisible = currentBeam.recentInstall == true
    }

    override fun observe() {
        super.observe()
        mViewModel.GroupInspectionIdList.observe(this) {
            var badNum = 0
            var completeNum = 0
            var goodNum = 0
            it.forEach { bean ->
                when (bean.statusCode) {
                    "1" -> {
                        goodNum += 1
                        completeNum += 1
                    }
                    "2" -> {
                        badNum += 1
                        completeNum += 1
                    }
                }
            }
            mList = FacilityInfoListBean(
                badNum = badNum.toString(),
                completeNum = completeNum,
                goodNum = goodNum.toString(),
                reList = it, sum = it.size.toString()
            )
            setViewStatas()
        }
        mViewModel.facilityInfoListResult.observe(this) {
            mList = it
            pointNext()
        }
        mViewModel.updateFacilityResult.observe(this) {
            if (it == 1) {
                mViewModel.getFacilityInfoList(mList!!.qrCodeIds)
            }
        }
        mViewModel.uploadImageResult.observe(this) {
            if (isAdd) {
                values.add(it)
                if (values.size >= 3) {
                    ivPhoto.visibility = View.INVISIBLE
                } else {
                    ivPhoto.visibility = View.VISIBLE
                }
            } else {
                values[mPosition] = it
            }
            mAdapter?.setList(values)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUESTCODE1) {
            val selectList = PictureSelector.obtainMultipleResult(data)
            if (selectList.size == 0) {
                return
            }
            mViewModel.uploadImage(File(selectList[0].compressPath))
        }
    }

    private var mDx = 0f
    private var mDy = 0f
    private var mActionEvent = 0
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mDx = event.x
                mDy = event.y
                mActionEvent = 0
                Log.i(TAG, "down=mDx=$mDx,mDy=$mDy")
            }
            MotionEvent.ACTION_MOVE -> {
                val mx = event.x
                val my = event.y
                val disW = mx - mDx //x轴滑动距离
                val disH: Float = my - mDy //y轴滑动距离
                if (abs(disW) > abs(disH) && abs(disW) > 20) { //滑动轴判断(这个条件可根据实际需求判断)
                    if (disW > 0) {
                        //往左滑
                        mActionEvent = 1
                        Log.i(TAG, "move=往右滑=mDx=$mDx,mDy=$mDy,mx=$mx,my=$my,disW=$disW,disH=$disH")
                    } else {
                        mActionEvent = 2
                        //往右滑
                        Log.i(TAG, "move=往左滑=mDx=$mDx,mDy=$mDy,mx=$mx,my=$my,disW=$disW,disH=$disH")
                    }
                }
                mDx = mx
                mDy = my
            }
            MotionEvent.ACTION_UP -> {
                if (mActionEvent == 1) {
                    pointLeft()
                } else if (mActionEvent == 2) {
                    pointRight()
                }
            }
        }
        return true
    }
}