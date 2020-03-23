package com.dedeandres.covnews.data.dashboard.entity

import com.dedeandres.covnews.domain.dashboard.entity.NewsModel
import com.google.gson.annotations.SerializedName

data class NewsDto(
    @SerializedName("status")
    var status: String,
    @SerializedName("articles")
    var articles: List<NewsBean>
)

data class NewsBean(
    @SerializedName("source")
    var sources: Sources,
    @SerializedName("author")
    var author: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("urlToImage")
    var urlToImage: String,
    @SerializedName("publishedAt")
    var publishedAt: String,
    @SerializedName("content")
    var content: String
)

data class Sources(
    @SerializedName("name")
    var name: String
)

fun List<NewsBean>.mapToDomain(): List<NewsModel> {
    return this.map {
        it.mapToDomain()
    }
}

fun NewsBean.mapToDomain(): NewsModel = NewsModel(
    sources = sources.name,
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage
)