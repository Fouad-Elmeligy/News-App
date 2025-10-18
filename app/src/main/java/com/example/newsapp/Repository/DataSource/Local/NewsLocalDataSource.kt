package com.example.newsapp.Repository.DataSource.Local

import com.example.newsapp.DataBase.NewsAppDataBase
import com.example.newsapp.Repository.DataSource.Remote.API.Model.SourcesItemDM
import com.example.newsapp.Screens.News.Resource

class NewsLocalDataSource {
    suspend fun getSavedSources(categoryId: String): Resource<List<SourcesItemDM>> {
        try {
            val sources = NewsAppDataBase.getInstance().getSourcesDao().getSavedSources(categoryId)
            return Resource.Success(sources)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Some thing went wrong")
        }
    }

    suspend fun saveSources(sources: List<SourcesItemDM>): Resource<Unit> {
        try {
            NewsAppDataBase.getInstance().getSourcesDao().saveSources(sources = sources)
            return Resource.Success(Unit)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "some thing went wrong")
        }
    }
}
