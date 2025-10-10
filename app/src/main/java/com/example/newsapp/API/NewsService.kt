package com.example.newsapp.API

import com.example.newsapp.API.Model.NewsResponse
import com.example.newsapp.API.Model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {
    @GET("sources")
    fun getSources(
        @Query("apiKey") apiKey: String = "502754b919904af2a6211e344bf3d685",
        @Query("category") categoryApiId: String? = null
    )
            : Call<SourcesResponse>

    @GET("everything")
    fun getNewsBySource(
        @Query("apiKey") apiKey: String = "502754b919904af2a6211e344bf3d685",
        @Query("sources") sourcesId: String? = null
    )
            : Call<NewsResponse>
}