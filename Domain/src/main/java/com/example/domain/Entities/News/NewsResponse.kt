package com.example.domain.Entities.News

data class NewsResponseEntity(

    val totalResults: Int? = null,
    val articles: List<ArticlesItemEntity>? = null,
    val message: String? = null,
    val status: String? = null
)


data class ArticlesItemEntity(

    val publishedAt: String? = null,

    val author: String? = null,

    val urlToImage: String? = null,

    val description: String? = null,

    val title: String? = null,

    val url: String? = null,

    val content: String? = null
)
