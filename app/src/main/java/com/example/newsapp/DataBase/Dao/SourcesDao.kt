package com.example.newsapp.DataBase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.Repository.DataSource.Remote.API.Model.SourcesItemDM


@Dao
interface SourcesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSources(sources: List<SourcesItemDM>)

    @Query("SELECT * FROM sources WHERE category =:category")
    suspend fun getSavedSources(category: String): List<SourcesItemDM>
}