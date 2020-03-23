package com.dedeandres.covnews.data.dashboard.api

import com.dedeandres.covnews.data.dashboard.entity.NewsDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface NewsApi {

    @GET("everything?q=berita%20corona&sortBy=publishedAt&apiKey=49e844a9e6404ef192be22d2bb37bed7")
    fun getNews(
        @Query("from") fromDate: Date?
    ): Single<NewsDto>

}