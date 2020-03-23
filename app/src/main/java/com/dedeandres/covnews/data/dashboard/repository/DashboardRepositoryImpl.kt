package com.dedeandres.covnews.data.dashboard.repository

import com.dedeandres.covnews.data.dashboard.api.DashboardApi
import com.dedeandres.covnews.data.dashboard.entity.mapToDomain
import com.dedeandres.covnews.domain.dashboard.entity.GlobalDataModel
import com.dedeandres.covnews.domain.dashboard.repository.DashboardRepository
import io.reactivex.Single

class DashboardRepositoryImpl(private val dashboardApi: DashboardApi) : DashboardRepository {

    override fun getGlobalData(): Single<List<GlobalDataModel>> {
        return dashboardApi.getGlobalData().map {
            it.mapToDomain()
        }
    }
}