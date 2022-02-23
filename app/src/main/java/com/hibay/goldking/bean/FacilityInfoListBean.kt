package com.hibay.goldking.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FacilityInfoListBean(
    var qrCodeIds: String? = null,
    val badNum: String? = null,
    val completeNum: Int? = null,
    val goodNum: String? = null,
    var reList: ArrayList<ReList>? = null,
    val sum: String? = null
) : Parcelable

@Parcelize
data class ReList(
    val facilityId: String?,
    val faultDesc: String?,
    val groupInspectionId: String?,
    val id: String?,
    val imageAddress: String?,
    val recentInstall: Boolean?,
    val picture: String?,
    val recentMaintain: Boolean?,
    val inspectionName: String?,
    val inspectionPerson: String?,
    val inspectionTime: String?,
    val location: String?,
    val name: String?,
    val status: String?,
    val statusCode: String?
) : Parcelable