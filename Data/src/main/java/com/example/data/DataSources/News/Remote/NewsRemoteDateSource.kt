package com.example.data.DataSources.News.Remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.Remote.Paging.NewsPagingSource
import com.example.data.Mapper.News.toEntity
import com.example.data.Mapper.News.toEntityFlow
import com.example.data.Models.News.ArticlesItemModel
import com.example.data.Models.News.SourcesResponseModel
import com.example.data.Remote.API.NewsService
import com.example.domain.Entities.News.ArticlesItemEntity
import com.example.domain.Entities.News.SourcesItemEntity
import com.example.domain.Repositories.News.NewsRemoteDataSource
import com.example.domain.Utils.Base.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow

class NewsRemoteDateSourceImpl(private val newsService: NewsService) : NewsRemoteDataSource  {

    override suspend fun fetchSources(categoryId: String): Resource<List<SourcesItemEntity>> {

        return try {
            val response = newsService.getSources(categoryApiId = categoryId)
            if (response.isSuccessful) {
                val sources = response.body()?.sources?.map { it.toEntity() } ?: listOf()
                 Resource.Success(sources)
            } else {
                val errorBody = response.errorBody()?.string()
                val gson = Gson()
                val sourceResponse = gson.fromJson(errorBody, SourcesResponseModel::class.java)
                return Resource.Error(
                    sourceResponse.message ?: "Something went wrong when fetching sources"
                )
            }
        } catch (e: Exception) {
             Resource.Error(e.message ?: "some thing went wrong when fetching sources")
        }
    }
    private var news: Flow<PagingData<ArticlesItemModel>>? = null
    var cacheSourceId: String? = null
    override suspend fun fetchNewsBySource(sourceId: String): Flow<PagingData<ArticlesItemEntity>> {
        if (cacheSourceId == sourceId && news != null) {
            return news?.toEntityFlow()!!
        }
        cacheSourceId = sourceId
        news = Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { NewsPagingSource(sourceId = sourceId, newsService = newsService) }).flow

        return news?.toEntityFlow()!!
    }


    
}