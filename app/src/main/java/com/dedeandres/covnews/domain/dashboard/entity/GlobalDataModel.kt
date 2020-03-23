package com.dedeandres.covnews.domain.dashboard.entity

import com.dedeandres.covnews.presenter.dashboard.entity.GlobalDataResult

data class GlobalDataModel(
    var id: String,
    val country: String,
    var lastUpdate: String,
    var lat: String,
    var long: String,
    var confirmed: String,
    var deaths: String,
    var recovered: String,
    var active: String
)

fun GlobalDataModel.mapToResult(): GlobalDataResult = GlobalDataResult(
    id = id,
    country = country,
    confirmed = confirmed,
    recovered = recovered,
    active = active,
    deaths = deaths,
    lastUpdate = lastUpdate
)

fun List<GlobalDataModel>.mapToResult(): List<GlobalDataResult> = this.map {
    it.mapToResult()
}