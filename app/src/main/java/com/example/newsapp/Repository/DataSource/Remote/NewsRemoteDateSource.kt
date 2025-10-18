package com.example.newsapp.Repository.DataSource.Remote

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.Repository.DataSource.Remote.API.ApiManager
import com.example.newsapp.Repository.DataSource.Remote.API.Model.ArticlesItem
import com.example.newsapp.Repository.DataSource.Remote.API.Model.SourcesItemDM
import com.example.newsapp.Repository.DataSource.Remote.API.Model.SourcesResponse
import com.example.newsapp.Repository.Paging.NewsPagingSource
import com.example.newsapp.Screens.News.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow

class NewsRemoteDateSource {

    suspend fun fetchSources(categoryId: String): Resource<List<SourcesItemDM>> {

        try {
            val response = ApiManager.getService().getSources(categoryApiId = categoryId)
            if (response.isSuccessful) {
                val sources = response.body()?.sources ?: listOf()
                return Resource.Success(sources)
            } else {
                val errorBody = response.errorBody()?.string()
                val gson = Gson()
                val sourceResponse = gson.fromJson(errorBody, SourcesResponse::class.java)
                return Resource.Error(
                    sourceResponse.message ?: "Something went wrong when fetching sources"
                )
            }
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "some thing went wrong when fetching sources")
        }
    }


    private var news: Flow<PagingData<ArticlesItem>>? = null
    var cacheSourceId: String? = null
    fun getRemotePagedNews(sourceId: String): Flow<PagingData<ArticlesItem>> {

        if (cacheSourceId == sourceId && news != null) {
            return news!!
        }
        cacheSourceId = sourceId
        news = Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { NewsPagingSource(sourceId) }).flow
        Log.e("NewRepository", "getPagedNews: INRepository")

        return news!!
    }
}