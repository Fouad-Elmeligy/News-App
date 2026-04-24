package com.example.data.Remote.API

import com.example.data.Models.News.NewsResponseModel
import com.example.data.Models.News.SourcesResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {
    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("category") categoryApiId: String? = null
    ): Response<SourcesResponseModel>

    @GET("everything")
    suspend fun getNewsBySource(
        @Query("sources") sourcesId: String? = null,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): Response<NewsResponseModel>

    @GET("everything")
    suspend fun getNewsBySourceSuspend(
        @Query("sources") sourcesId: String? = null,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): NewsResponseModel
}