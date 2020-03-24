package com.dedeandres.covnews.data.dashboard.repository

import com.dedeandres.covnews.data.dashboard.api.DashboardApi
import com.dedeandres.covnews.data.dashboard.api.NewsApi
import com.dedeandres.covnews.data.dashboard.entity.Hotline
import com.dedeandres.covnews.data.dashboard.entity.getListHotline
import com.dedeandres.covnews.data.dashboard.entity.mapToDomain
import com.dedeandres.covnews.domain.dashboard.entity.GlobalDataModel
import com.dedeandres.covnews.domain.dashboard.entity.NewsModel
import com.dedeandres.covnews.domain.dashboard.repository.DashboardRepository
import com.dedeandres.covnews.util.dateSimpleFormat
import io.reactivex.Single

class DashboardRepositoryImpl(private val dashboardApi: DashboardApi, private val newsApi: NewsApi) : DashboardRepository {

    override fun getGlobalData(): Single<List<GlobalDataModel>> {
        return dashboardApi.getGlobalData().map {
            it.mapToDomain()
        }
    }

    override fun getNews(): Single<List<NewsModel>> {
        return newsApi.getNews(dateSimpleFormat()).map {
            it.articles.mapToDomain()
        }
    }

    override fun getHotline(): Single<List<Hotline>> {
        return Single.just(getListHotline())
    }
}