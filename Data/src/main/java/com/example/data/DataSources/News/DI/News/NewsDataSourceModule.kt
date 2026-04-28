package com.example.data.DataSources.News.DI.News

import com.example.data.DataSources.News.Local.NewsLocalDataSourceImpl
import com.example.data.DataSources.News.Remote.NewsRemoteDateSourceImpl
import com.example.domain.Repositories.News.NewsLocalDataSource
import com.example.domain.Repositories.News.NewsRemoteDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val NewsDataSourceModule = module {

    singleOf(::NewsLocalDataSourceImpl) bind NewsLocalDataSource::class

    singleOf(::NewsRemoteDateSourceImpl) bind NewsRemoteDataSource::class
}