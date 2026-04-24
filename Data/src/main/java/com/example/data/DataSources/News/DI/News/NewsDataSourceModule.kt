package com.example.data.DataSources.News.DI.News

import com.example.data.DataSources.News.Local.NewsLocalDataSourceImpl
import com.example.data.DataSources.News.Remote.NewsRemoteDateSourceImpl
import com.example.domain.Repositories.News.NewsLocalDataSource
import com.example.domain.Repositories.News.NewsRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindsNewsRemoteDataSource(newsRemoteDateSourceImpl: NewsRemoteDateSourceImpl): NewsRemoteDataSource

    @Binds
    abstract fun bindsNewsLocalDataSource(newsLocalDataSourceImpl: NewsLocalDataSourceImpl): NewsLocalDataSource


}