package com.hibay.goldking.ui.act

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.Gravity
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
import com.hibay.goldking.common.showAlertDialog
import com.hibay.goldking.common.showToast
import com.hibay.goldking.ui.ChoseDevicesDialog
import com.hibay.goldking.ui.viewmodel.InspectionViewMoel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.activity_inspection.*
import kotlinx.android.synthetic.main.mytoolbar.*
import java.io.File

class InspectionDetailActivity : BaseVmActivity<InspectionViewMoel>(R.layout.activity_inspection) {
    override fun viewModelClass() = InspectionViewMoel::class.java
    var mAdapter: BaseQuickAdapter<String, BaseViewHolder>? = null
    private val values = ArrayList<String>()
    private var photoPath: String? = null
    private var mPosition = 0
    val REQUESTCODE2 = 31
    val REQUESTCODE1 = 30
    override fun initView() {
        super.initView()
        BarUtils.transparentStatusBar(this)
        BarUtils.addMarginTopEqualStatusBarHeight(tvTitle)
        tvTitle.text = "开始巡检"
        ivBack.setOnClickListener { ActivityHelper.finish(InspectionDetailActivity::class.java) }
        ivPhoto.setOnClickListener {
            PictureSelector.create(this@InspectionDetailActivity)
                .openGallery(PictureMimeType.ofImage())//相册和拍照
                .selectionMode(PictureConfig.SINGLE)
                .imageEngine(GlideEngine.createGlideEngine())
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .isCompress(true) //压缩
                .cutOutQuality(50) //压缩后图片质量
                .forResult(REQUESTCODE1)
        }

        initRecycler()
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
                Glide.with(context).load(File(item)).transform(RoundedCorners(ConvertUtils.dp2px(5f)))
                    .into(holder.getView<View>(R.id.ivImage) as ImageView)
            }
        }
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            PermissionUtils.permissionGroup(PermissionConstants.CAMERA, PermissionConstants.STORAGE)
                .callback(object : PermissionUtils.SimpleCallback {
                    override fun onGranted() {
                        mPosition = position
                        PictureSelector.create(this@InspectionDetailActivity)
                            .openGallery(PictureMimeType.ofImage())//相册和拍照
                            .selectionMode(PictureConfig.SINGLE)
                            .imageEngine(GlideEngine.createGlideEngine())
                            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                            .isCompress(true) //压缩
                            .cutOutQuality(50) //压缩后图片质量
                            .forResult(REQUESTCODE2)
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
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerview.layoutManager = linearLayoutManager
        recyclerview.adapter = mAdapter
    }


    var hasNext = false
    private fun pointRight() {
        hasNext = false
        run {
            mList?.reList?.forEachIndexed { index, item ->
                if (item.status.isNullOrEmpty() && index >= currentNum) {
                    hasNext = true
                    currentNum = index
                    currentBeam = item
                    notifySelcet()
                    return
                }
            }
        }
        if (!hasNext) {
            showToast("后面没有待巡检的")
        }
    }

    private fun pointLeft() {
        if (currentNum > 0) {
            currentNum -= 1
            currentBeam = mList?.reList!![currentNum]
            notifySelcet()
        } else {
            showToast("已经是第一个了")
        }
    }

    private fun notifySelcet() {
        group.isVisible = false
        etFailMessage.text.clear()
        values.clear()
        mAdapter?.notifyDataSetChanged()

        initeCurrentDevice(currentBeam)

        tvCurrentNum.text = "" + (currentNum + 1) + "/" + mList?.sum
        tvSuccesNum.text = mList?.goodNum
        tvFailNum.text = mList?.badNum

        if (loandialog != null) {
            loandialog?.refreshUI(mList, currentNum)
        }
    }

    var mList: FacilityInfoListBean? = null
    var currentNum = 0
    var currentBeam: ReList? = null
    override fun initData() {
        super.initData()
        intent.getStringExtra("id")?.let {
            mViewModel.getFacilityInfoById(it)
        }
        intent.getParcelableExtra<FacilityInfoListBean>("facilityInfoListResult")?.let {
            mList = it
            clDialog.isVisible = true
            llLeft.isVisible = true
            llRight.isVisible = true
            currentNum = 0
            pointRight()
        }

        tvCancle.setOnClickListener {
            group.isVisible = false
        }
        tvConfirm.setOnClickListener {
            mViewModel.updateFacilityStatus(currentBeam, "2", faultDesc = etFailMessage.text.toString())
        }
        tvSucces.setOnClickListener {
            if (group.isVisible) {
                showAlertDialog("确认设备正常吗？") {
                    group.isVisible = false
                    mViewModel.updateFacilityStatus(currentBeam, "1")
                }
            } else {
                mViewModel.updateFacilityStatus(currentBeam, "1")
            }
        }
        tvFail.setOnClickListener {
            group.isVisible = true
        }

    }

    private fun initeCurrentDevice(currentBeam: ReList?) {
        if (currentBeam == null) return
        Glide.with(this).load(currentBeam.imageAddress).apply(
            RequestOptions().fallback(R.drawable.devices_logo).error(R.drawable.devices_logo)
        ).into(ivDevicesLogo)
        tvPosition.text = (currentNum + 1).toString()
        tvDeviceName.text = currentBeam.name
        tvDeviceID.text = currentBeam.facilityId
        tvLocation.text = currentBeam.location
    }

    override fun observe() {
        super.observe()
        mViewModel.facilityInfo.observe(this) {
            currentBeam = it
            initeCurrentDevice(it)
        }
        mViewModel.updateFacilityResult.observe(this) {
            if (it == 1) {
                showToast("巡检成功")
                mList?.let {
                    pointRight()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val selectList = PictureSelector.obtainMultipleResult(data)
            if (selectList.size == 0) {
                return
            }
            photoPath = selectList[0].compressPath
            if (requestCode == REQUESTCODE1) {
                values.add(photoPath!!)
                if (values.size >= 3) {
                    ivPhoto.visibility = View.INVISIBLE
                } else {
                    ivPhoto.visibility = View.VISIBLE
                }
            } else if (requestCode == REQUESTCODE2) {
                values[mPosition] = photoPath!!
            }
            mAdapter?.setList(values)
        }
    }

}