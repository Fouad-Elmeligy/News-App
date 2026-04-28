package com.example.data.DataSources.News.Local

import com.example.data.Local.DataBase.NewsAppDataBase
import com.example.data.Mapper.News.toEntity
import com.example.data.Mapper.News.toModel
import com.example.domain.Entities.News.SourcesItemEntity
import com.example.domain.Repositories.News.NewsLocalDataSource
import com.example.domain.Utils.Base.Resource
class NewsLocalDataSourceImpl (private val roomDatabase: NewsAppDataBase): NewsLocalDataSource {

    override suspend fun getSources(categoryId: String): Resource<List<SourcesItemEntity>> {
       return try {
            val sources = roomDatabase.getSourcesDao().getSavedSources(categoryId).map {
                it.toEntity()
            }
           Resource.Success(sources)
        } catch (e: Exception) {
             Resource.Error(e.message ?: "Some thing went wrong")
        }
    }

    override suspend fun saveSources(source: List<SourcesItemEntity>): Resource<Unit> {
        return try {
            val sourcesModels = source.map { it.toModel() }
            roomDatabase.getSourcesDao().saveSources(sources = sourcesModels)
             Resource.Success(Unit)
        } catch (e: Exception) {
             Resource.Error(e.message ?: "some thing went wrong")
        }
    }
}
