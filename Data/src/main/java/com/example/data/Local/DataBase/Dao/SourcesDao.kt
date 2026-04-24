package com.example.data.Local.DataBase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.Models.News.SourcesItemModel



@Dao
interface SourcesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSources(sources: List<SourcesItemModel>)

    @Query("SELECT * FROM sources WHERE category =:category")
    suspend fun getSavedSources(category: String): List<SourcesItemModel>
}