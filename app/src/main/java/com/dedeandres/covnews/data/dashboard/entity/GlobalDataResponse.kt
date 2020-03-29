package com.dedeandres.covnews.data.dashboard.entity

import com.dedeandres.covnews.domain.dashboard.entity.GlobalDataModel
import com.dedeandres.covnews.util.toIndonesiaDateFormat
import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder
import java.util.*


data class GlobalDataDto(
    @SerializedName("attributes")
    var attributes: AttributesBean
)

data class AttributesBean(
    @SerializedName("OBJECTID")
    var objectId: Int,
    @SerializedName("Country_Region")
    val countryRegion: String,
    @SerializedName("Last_Update")
    var lastUpdate: Long,
    @SerializedName("Lat")
    var lat: Float,
    @SerializedName("Long_")
    var long: Float,
    @SerializedName("Confirmed")
    var confirmed: Int,
    @SerializedName("Deaths")
    var deaths: Int,
    @SerializedName("Recovered")
    var recovered: Int,
    @SerializedName("Active")
    var active: Int
)

fun List<GlobalDataDto>.mapToDomain(): List<GlobalDataModel> {
    return this.map {
        GlobalDataModel(
            id = it.attributes.objectId.toString(),
            country = changeCountryName(it.attributes.countryRegion),
            confirmed = it.attributes.confirmed.toString(),
            lastUpdate = StringBuilder(Date(it.attributes.lastUpdate).toIndonesiaDateFormat()).append(" WIB").toString(),
            active = it.attributes.active.toString(),
            deaths = it.attributes.deaths.toString(),
            lat = it.attributes.lat.toString(),
            long = it.attributes.long.toString(),
            recovered = it.attributes.recovered.toString()
        )
    }
}

private fun changeCountryName(countryName: String): String {
    return when(countryName) {
        "Korea, South" -> {
            "South Korea"
        }
        "Taiwan*" -> {
            "Taiwan"
        }
        else -> {
            countryName
        }
    }
}