package com.dedeandres.covnews.domain.dashboard.repository

import com.dedeandres.covnews.domain.dashboard.entity.GlobalDataModel
import com.dedeandres.covnews.domain.dashboard.entity.NewsModel
import io.reactivex.Single

interface DashboardRepository {

    fun getGlobalData(): Single<List<GlobalDataModel>>

    fun getNews(): Single<List<NewsModel>>

}