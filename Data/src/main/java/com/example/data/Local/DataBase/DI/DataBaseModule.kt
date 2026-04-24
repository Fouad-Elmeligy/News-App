package com.example.data.Local.DataBase.DI

import android.content.Context
import androidx.room.Room
import com.example.data.Local.DataBase.NewsAppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun providesNewsDataBaseInstance(@ApplicationContext context: Context): NewsAppDataBase {
        return  synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                NewsAppDataBase::class.java,
                "NewsDataBase"
            ).fallbackToDestructiveMigration(true).build()

        }
    }
}