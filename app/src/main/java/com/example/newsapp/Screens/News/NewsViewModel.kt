package com.example.newsapp.Screens.News

import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn

import com.example.domain.Entities.News.ArticlesItemEntity
import com.example.domain.Entities.News.SourcesItemEntity

import com.example.domain.UseCases.News.GetNewsBySourceUseCase
import com.example.domain.UseCases.News.GetSourcesUseCase
import com.example.domain.Utils.Base.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsBySourceUseCase: GetNewsBySourceUseCase,
    private val getSourcesUseCase: GetSourcesUseCase
) : ViewModel() {
    private val _sourcesResource =
        MutableStateFlow<Resource<List<SourcesItemEntity>>>(Resource.Initial())
    val sourcesResource: StateFlow<Resource<List<SourcesItemEntity>>> =
        _sourcesResource.asStateFlow()

    private var currentSourceId: String? = null
    private var currentNewsArticlesFlow: Flow<PagingData<ArticlesItemEntity>>? = null
    fun getPagedNews(sourceId: String): Flow<PagingData<ArticlesItemEntity>> {
        if (currentSourceId == sourceId && currentNewsArticlesFlow != null){
            return currentNewsArticlesFlow!!
        }
        viewModelScope.launch {
            currentNewsArticlesFlow = getNewsBySourceUseCase(sourceId).cachedIn(viewModelScope)

        }
        currentSourceId = sourceId


        Log.e("getPageNews", "getPagedNews: Running")
        return currentNewsArticlesFlow!!
    }

    fun getSources(categoryApiId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _sourcesResource.value = Resource.Loading()
            Log.e("VM", "▶ Calling getSources with: $categoryApiId")
            try {
                val response = getSourcesUseCase.invoke(categoryApiId)
                Log.e("VM", "✅ Response type: ${response::class.simpleName}")
                when (response) {
                    is Resource.Success -> Log.e("VM", "✅ Sources count: ${response.data.size}")
                    is Resource.Error -> Log.e("VM", "❌ Error: ${response.errorMessage}")
                    else -> Log.e("VM", "⚠ Other: $response")
                }
                _sourcesResource.value = response
            } catch (e: Exception) {
                Log.e("VM", "💥 Exception: ${e.message}")
                _sourcesResource.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }


}