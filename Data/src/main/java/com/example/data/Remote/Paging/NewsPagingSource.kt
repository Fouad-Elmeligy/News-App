package com.example.data.Remote.Paging


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.Models.News.ArticlesItemModel
import com.example.data.Remote.API.NewsService
import javax.inject.Inject

class NewsPagingSource @Inject constructor (
    private val newsService: NewsService,val sourceId: String
    ) : PagingSource<Int, ArticlesItemModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItemModel> {
        try {


            val currentPage = params.key ?: 1
            val response = newsService
                .getNewsBySourceSuspend(sourcesId = sourceId, page = currentPage)
            val articles =response.articles?:emptyList()
            val prevKey = if(currentPage==1)null else currentPage -1
            val nextKey = if(articles.isEmpty()) null else currentPage+1

            return LoadResult.Page(data = articles, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticlesItemModel>): Int? {
        return state.anchorPosition?.let { position->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }
    }
}
