package com.dedeandres.covnews.presenter.dashboard.entity

data class IndonesiaDataResult(
    var fid: String,
    val provinceCode: String,
    var province: String,
    var positive: String,
    var recovered: String,
    var deaths: String
)