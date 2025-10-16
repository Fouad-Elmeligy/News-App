package com.example.newsapp.Repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.API.Model.ArticlesItem
import com.example.newsapp.Paging.NewsPagingSource
import kotlinx.coroutines.flow.Flow
class NewsRepository {
    companion object {
        var counter=0
    }
    private var news: Flow<PagingData<ArticlesItem>>? = null
    var cacheSourceId: String? = null
    fun getPagedNews(sourceId: String): Flow<PagingData<ArticlesItem>> {
        ++counter
        Log.e("NewsRepository", "RepositoryAccessTimes-> $counter", )
        if (cacheSourceId == sourceId && news != null){
            return news!!
        }
        cacheSourceId = sourceId
        news = Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { NewsPagingSource(sourceId) }).flow
        Log.e("NewRepository", "getPagedNews: INRepository", )

        return news!!
    }
}