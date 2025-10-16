package com.example.newsapp.API

import com.example.newsapp.API.Model.NewsResponse
import com.example.newsapp.API.Model.SourcesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {
    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String = "177f7f7acb824932b1b50ae431bcb5da",
        @Query("category") categoryApiId: String? = null
    )
            : Response<SourcesResponse>

    @GET("everything")
    suspend fun getNewsBySource(
        @Query("apiKey") apiKey: String = "177f7f7acb824932b1b50ae431bcb5da",
        @Query("sources") sourcesId: String? = null,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    )
            : Response<NewsResponse>

    @GET("everything")
    suspend fun getNewsBySourceSuspend(
        @Query("apiKey") apiKey: String = "177f7f7acb824932b1b50ae431bcb5da",
        @Query("sources") sourcesId: String? = null,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): NewsResponse
}