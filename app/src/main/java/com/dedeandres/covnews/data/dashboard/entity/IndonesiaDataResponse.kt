package com.dedeandres.covnews.data.dashboard.entity

import com.dedeandres.covnews.domain.dashboard.entity.IndonesiaDataModel
import com.google.gson.annotations.SerializedName

data class IndonesiaDataDto(
    @SerializedName("attributes")
    var attributes: IndonesiaAttributesBean
)

data class IndonesiaAttributesBean(
    @SerializedName("FID")
    var fid: Int,
    @SerializedName("Kode_Provi")
    val provinceCode: String,
    @SerializedName("Provinsi")
    var province: String,
    @SerializedName("Kasus_Posi")
    var positive: Int,
    @SerializedName("Kasus_Semb")
    var recovered: Int,
    @SerializedName("Kasus_Meni")
    var deaths: Int
)


fun List<IndonesiaDataDto>.mapToDomain(): List<IndonesiaDataModel> {
    return this.map {
        IndonesiaDataModel(
            fid = it.attributes.fid,
            deaths = it.attributes.deaths,
            recovered = it.attributes.recovered,
            positive = it.attributes.positive,
            province = it.attributes.province,
            provinceCode = it.attributes.provinceCode
        )
    }
}