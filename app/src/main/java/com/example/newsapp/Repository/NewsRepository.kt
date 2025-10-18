package com.example.newsapp.Repository

import android.content.Context
import androidx.paging.PagingData
import com.example.newsapp.Repository.DataSource.Local.NewsLocalDataSource
import com.example.newsapp.Repository.DataSource.Remote.API.Model.ArticlesItem
import com.example.newsapp.Repository.DataSource.Remote.API.Model.SourcesItemDM
import com.example.newsapp.Repository.DataSource.Remote.NewsRemoteDateSource
import com.example.newsapp.Screens.News.Resource
import com.example.newsapp.Utils.NetworkAvailable
import kotlinx.coroutines.flow.Flow

class NewsRepository {
    val newsLocalDataSource = NewsLocalDataSource()
    val newsRemoteDataSource = NewsRemoteDateSource()
    suspend fun getSources(categoryId: String, context: Context): Resource<List<SourcesItemDM>> {
        val network = NetworkAvailable()
        val isConnected = network.isNetworkAvailable(context)
        if (isConnected) {
            val sources = newsRemoteDataSource.fetchSources(categoryId)
            if (sources is Resource.Success) {
                newsLocalDataSource.saveSources(sources.data)
                sources
            }
        }
        return newsLocalDataSource.getSavedSources(categoryId)

    }

    fun getPagedNews(sourceId: String): Flow<PagingData<ArticlesItem>> {

        return newsRemoteDataSource.getRemotePagedNews(sourceId)
    }
}