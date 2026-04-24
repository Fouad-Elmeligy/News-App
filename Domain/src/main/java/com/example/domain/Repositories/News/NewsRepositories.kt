package com.example.domain.Repositories.News

import android.content.Context
import androidx.paging.PagingData
import com.example.domain.Entities.News.ArticlesItemEntity
import com.example.domain.Entities.News.SourcesItemEntity
import com.example.domain.Utils.Base.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getSources(categoryId: String): Resource<List<SourcesItemEntity>>
    suspend fun getNewsBySource(sourceId: String):Flow<PagingData<ArticlesItemEntity>>
}
interface NewsLocalDataSource{
    suspend fun getSources(categoryId: String): Resource<List<SourcesItemEntity>>
    suspend fun saveSources(source:List<SourcesItemEntity>): Resource<Unit>

}

interface NewsRemoteDataSource {
    suspend fun fetchSources(categoryId: String): Resource<List<SourcesItemEntity>>
    suspend fun fetchNewsBySource(sourceId: String): Flow<PagingData<ArticlesItemEntity>>
}