package com.example.newsapp.ui.theme.Screens.News

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.illegalDecoyCallException
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.API.ApiManager
import com.example.newsapp.API.Model.ArticlesItem
import com.example.newsapp.API.Model.NewsResponse
import com.example.newsapp.API.Model.SourcesItemDM
import com.example.newsapp.API.Model.SourcesResponse
import com.example.newsapp.Repository.NewsRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class NewsViewModel : ViewModel() {

    val repository = NewsRepository()
    val sourcesList = mutableStateListOf<SourcesItemDM>()
    val articlesList = mutableStateListOf<ArticlesItem>()
    val sourcesError = mutableStateOf("")
    val articlesError = mutableStateOf("")

    @Composable
    fun getPagedNews(sourceId: String): LazyPagingItems<ArticlesItem> {
        Log.e("getPageNews", "getPagedNews: Running")
        return repository.getPagedNews(sourceId).collectAsLazyPagingItems()
    }

    fun getSources(categoryApiId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = async { ApiManager.getService()
                    .getSources(categoryApiId = categoryApiId) }.await()
                if(response.isSuccessful){

                    Log.e("Response", "Code: ${response.code()} Body: ${response.body()}")
                    sourcesList.addAll(response.body()?.sources ?: listOf())
                }else{
                    val errorBody = response.errorBody()?.string()
                    val gson = Gson()
                    val sourceResponse = gson.fromJson(errorBody, SourcesResponse::class.java)
                    sourcesError.value=sourceResponse?.message?:"Something Went Wrong"
                }

            } catch (e: Exception) {
                sourcesError.value=e.message?:"Something Went Wrong"
            }
        }
    }

    fun getNewsBySourceId(sourceId: String): List<ArticlesItem> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiManager.getService().getNewsBySource(sourcesId = sourceId)
                if (response.isSuccessful) {
                    articlesList.clear()
                    articlesList.addAll(response.body()?.articles ?: emptyList())
                } else {
                    val errorBody = response.errorBody()?.string()
                    val gson = Gson()
                    val articlesResponse = gson.fromJson(errorBody, NewsResponse::class.java)
                    articlesError.value=articlesResponse.message?:"Something Went Wrong"
                }

            } catch (e: Exception) {
                articlesError.value=e.message?:"Something Went Wrong"
            }
        }
        return articlesList
    }


}