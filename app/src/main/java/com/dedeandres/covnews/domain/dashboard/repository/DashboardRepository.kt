package com.dedeandres.covnews.domain.dashboard.repository

import com.dedeandres.covnews.domain.dashboard.entity.GlobalDataModel
import io.reactivex.Single

interface DashboardRepository {

    fun getGlobalData(): Single<List<GlobalDataModel>>

}