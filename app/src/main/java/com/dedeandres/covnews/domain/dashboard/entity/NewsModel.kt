package com.dedeandres.covnews.domain.dashboard.entity

import com.dedeandres.covnews.presenter.dashboard.entity.NewsResult

data class NewsModel(
    var sources: String,
    var author: String,
    var title: String,
    var description: String,
    var url: String,
    var urlToImage: String,
    var publishedAt: String,
    var content: String
)

fun NewsModel.mapToResult(): NewsResult = NewsResult(
    sources = sources,
    urlToImage = urlToImage,
    url = url,
    title = title,
    publishedAt = publishedAt,
    description = description,
    content = content,
    author = author
)