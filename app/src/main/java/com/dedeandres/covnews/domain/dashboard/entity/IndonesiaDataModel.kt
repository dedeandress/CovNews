package com.dedeandres.covnews.domain.dashboard.entity

import com.dedeandres.covnews.presenter.dashboard.entity.IndonesiaDataResult

data class IndonesiaDataModel(
    var fid: Int,
    val provinceCode: String,
    var province: String,
    var positive: Int,
    var recovered: Int,
    var deaths: Int
)

fun List<IndonesiaDataModel>.mapToResult(): List<IndonesiaDataResult> {
    return this.map {
        IndonesiaDataResult(
            fid = it.fid.toString(),
            provinceCode = it.provinceCode,
            province = it.province,
            recovered = it.recovered.toString(),
            deaths = it.deaths.toString(),
            positive = it.positive.toString()
        )
    }
}