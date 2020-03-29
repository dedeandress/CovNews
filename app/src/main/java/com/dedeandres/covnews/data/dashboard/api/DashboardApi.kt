package com.dedeandres.covnews.data.dashboard.api

import com.dedeandres.covnews.data.dashboard.entity.*
import io.reactivex.Single
import retrofit2.http.GET

interface DashboardApi {

    @GET("/")
    fun getGlobalData(): Single<List<GlobalDataDto>>

    @GET("/indonesia/provinsi")
    fun getIndonesiaData(): Single<List<IndonesiaDataDto>>

}