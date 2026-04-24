package com.example.data.Mapper.News
import androidx.paging.map
import kotlinx.coroutines.flow.map
import androidx.paging.PagingData
import com.example.data.Models.News.ArticlesItemModel
import com.example.data.Models.News.SourcesItemModel
import com.example.domain.Entities.News.ArticlesItemEntity
import com.example.domain.Entities.News.SourcesItemEntity
import kotlinx.coroutines.flow.Flow


fun SourcesItemModel.toEntity()= SourcesItemEntity(country,name,description,language,id,country,url)

fun ArticlesItemModel.toEntity()= ArticlesItemEntity(publishedAt,author,urlToImage,description,title,url,content)

fun SourcesItemEntity.toModel()= SourcesItemModel(country,name,description,language,id,country,url)
fun Flow<PagingData<ArticlesItemModel>>.toEntityFlow():Flow<PagingData<ArticlesItemEntity>>{
    return this.map { pagingData->
        pagingData.map { ArticlesItemModel->
            ArticlesItemModel.toEntity()
        }
    }
}