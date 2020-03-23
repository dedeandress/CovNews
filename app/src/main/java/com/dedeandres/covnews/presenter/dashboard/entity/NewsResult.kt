package com.dedeandres.covnews.presenter.dashboard.entity

data class NewsResult(
    var sources: String,
    var author: String,
    var title: String,
    var description: String,
    var url: String,
    var urlToImage: String,
    var publishedAt: String,
    var content: String
)