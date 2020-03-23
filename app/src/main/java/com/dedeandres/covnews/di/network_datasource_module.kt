package com.dedeandres.covnews.di

import com.dedeandres.covnews.data.dashboard.api.DashboardApi
import com.dedeandres.covnews.data.dashboard.api.NewsApi
import com.dedeandres.covnews.di.DatasourceProperties.NEWS_SERVER_URL
import com.dedeandres.covnews.di.DatasourceProperties.SERVER_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkDataSourceModule = applicationContext {
    bean {
        createOkHttpClient()
    }

    bean {
        createWebService<DashboardApi>(get(), SERVER_URL)
    }

    bean {
        createWebService<NewsApi>(get(), NEWS_SERVER_URL)
    }
}

object DatasourceProperties {
    const val SERVER_URL = "https://api.kawalcorona.com"
    const val NEWS_SERVER_URL = "http://newsapi.org/v2/"
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}