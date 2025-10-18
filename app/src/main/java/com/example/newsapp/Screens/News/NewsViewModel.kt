package com.example.newsapp.Screens.News

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.Repository.DataSource.Remote.API.ApiManager
import com.example.newsapp.Repository.DataSource.Remote.API.Model.ArticlesItem
import com.example.newsapp.Repository.DataSource.Remote.API.Model.NewsResponse
import com.example.newsapp.Repository.DataSource.Remote.API.Model.SourcesItemDM
import com.example.newsapp.Repository.NewsRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    val repository = NewsRepository()
    val sourcesResource = mutableStateOf<Resource<List<SourcesItemDM>>>(Resource.Initial())
    val articlesResource = mutableStateOf<Resource<List<ArticlesItem>>>(Resource.Initial())


    @Composable
    fun getPagedNews(sourceId: String): LazyPagingItems<ArticlesItem> {
        Log.e("getPageNews", "getPagedNews: Running")
        return repository.getPagedNews(sourceId).collectAsLazyPagingItems()
    }

    fun getSources(categoryApiId: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            sourcesResource.value = Resource.Loading()
            val response = async { repository.getSources(categoryApiId, context) }.await()
            sourcesResource.value = response
        }
    }

    fun getNewsBySourceId(sourceId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            articlesResource.value = Resource.Loading()
            try {
                val response = ApiManager.getService().getNewsBySource(sourcesId = sourceId)
                if (response.isSuccessful) {
                    articlesResource.value =
                        Resource.Success(response.body()?.articles ?: emptyList())
                } else {
                    val errorBody = response.errorBody()?.string()
                    val gson = Gson()
                    val articlesResponse = gson.fromJson(errorBody, NewsResponse::class.java)
                    articlesResource.value =
                        Resource.Error(articlesResponse.message ?: "Something Went Wrong")
                }

            } catch (e: Exception) {
                articlesResource.value = Resource.Error(e.message ?: "Something Went Wrong")
            }
        }
    }


}