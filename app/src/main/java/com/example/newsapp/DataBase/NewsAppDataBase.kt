package com.example.newsapp.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.Repository.DataSource.Remote.API.Model.SourcesItemDM
import com.example.newsapp.DataBase.Dao.SourcesDao


@Database(entities = [SourcesItemDM::class], version = 1)
abstract class NewsAppDataBase : RoomDatabase() {
    abstract fun getSourcesDao(): SourcesDao

    companion object {
        private var INSTANCE: NewsAppDataBase? = null
        fun initDataBase(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = synchronized(this) {
                   Room.databaseBuilder(
                        context.applicationContext,
                        NewsAppDataBase::class.java,
                        "NewsDataBase"
                    ).fallbackToDestructiveMigration(true).build()

                }
            }
        }

        fun getInstance(): NewsAppDataBase {
            return INSTANCE!!
        }
    }
}