package com.dedeandres.covnews.presenter.dashboard.entity

data class GlobalDataResult(
    var id: String,
    val country: String,
    var lastUpdate: String,
    var confirmed: String,
    var deaths: String,
    var recovered: String,
    var active: String
)