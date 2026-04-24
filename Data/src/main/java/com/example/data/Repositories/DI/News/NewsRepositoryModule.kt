package com.example.data.Repositories.DI.News


import com.example.data.Repositories.NewsRepository.NewsRepositoryImpl
import com.example.domain.Repositories.News.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}