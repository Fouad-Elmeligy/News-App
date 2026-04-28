package com.example.data.Repositories.NewsRepository

import androidx.paging.PagingData
import com.example.data.Utils.NetworkAvailable
import com.example.domain.Entities.News.ArticlesItemEntity
import com.example.domain.Entities.News.SourcesItemEntity
import com.example.domain.Repositories.News.NewsLocalDataSource
import com.example.domain.Repositories.News.NewsRemoteDataSource
import com.example.domain.Repositories.News.NewsRepository
import com.example.domain.Utils.Base.Resource
import kotlinx.coroutines.flow.Flow
class NewsRepositoryImpl (
    private val newsLocalDataSource :NewsLocalDataSource,
   private val newsRemoteDataSource :NewsRemoteDataSource,
    private val networkConnection: NetworkAvailable
) : NewsRepository {


    override suspend fun getSources(
        categoryId: String,
    ): Resource<List<SourcesItemEntity>> {
        val isConnected = networkConnection.isNetworkAvailable()
        return if (isConnected) {
            val sources = newsRemoteDataSource.fetchSources(categoryId)
            if (sources is Resource.Success) {
                newsLocalDataSource.saveSources(sources.data)

            }
            sources
        }else{
         newsLocalDataSource.getSources(categoryId)
    }}

    override suspend fun getNewsBySource(sourceId: String): Flow<PagingData<ArticlesItemEntity>> {
        return newsRemoteDataSource.fetchNewsBySource(sourceId)
    }
}