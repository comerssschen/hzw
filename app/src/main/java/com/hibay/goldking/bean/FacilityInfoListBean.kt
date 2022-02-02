package com.hibay.goldking.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FacilityInfoListBean(
    val badNum: String,
    val completeNum: Int,
    val goodNum: String,
    val reList: ArrayList<ReList>,
    val sum: String
) : Parcelable

@Parcelize
data class ReList(
    val facilityId: String,
    val faultDesc: String?,
    val groupInspectionId: String,
    val id: String,
    val imageAddress: String?,
    val inspectionName: String,
    val inspectionPerson: String,
    val inspectionTime: String,
    val location: String,
    val name: String,
    val status: String?
) : Parcelable