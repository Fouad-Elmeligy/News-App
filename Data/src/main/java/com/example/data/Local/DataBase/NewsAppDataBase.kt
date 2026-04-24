package com.example.data.Local.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.Local.DataBase.Dao.SourcesDao
import com.example.data.Models.News.SourcesItemModel


@Database(entities = [SourcesItemModel::class], version = 1, exportSchema = false)
abstract class NewsAppDataBase : RoomDatabase() {
    abstract fun getSourcesDao(): SourcesDao
    }
