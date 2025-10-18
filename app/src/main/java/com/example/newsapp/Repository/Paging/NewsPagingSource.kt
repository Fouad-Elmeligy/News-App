package com.example.newsapp.Repository.Paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.Repository.DataSource.Remote.API.ApiManager
import com.example.newsapp.Repository.DataSource.Remote.API.Model.ArticlesItem

class NewsPagingSource(val sourceId: String) : PagingSource<Int, ArticlesItem>() {
    companion object {
        var counter =0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        try {

            val currentPage = params.key ?: 1
            val response = ApiManager.getService()
                .getNewsBySourceSuspend(sourcesId = sourceId, page = currentPage)
            val articles =response.articles?:emptyList()
            val prevKey = if(currentPage==1)null else currentPage -1
            val nextKey = if(articles.isEmpty()) null else currentPage+1
            ++counter
            Log.e("PagingSource", "PagingSourceLoadTimes-> $counter", )
            return LoadResult.Page(data = articles, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition?.let { position->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }
    }
}
