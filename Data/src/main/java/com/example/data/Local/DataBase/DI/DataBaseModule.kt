package com.example.data.Local.DataBase.DI

import androidx.room.Room
import com.example.data.Local.DataBase.NewsAppDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val DataBaseModule = module {
    single {
        synchronized(this) {
            Room.databaseBuilder(
                androidContext(),
                NewsAppDataBase::class.java,
                "NewsDataBase"
            ).fallbackToDestructiveMigration().build()

        }
    }
}