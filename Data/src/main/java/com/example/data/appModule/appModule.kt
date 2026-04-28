package com.example.data.appModule

import com.example.data.DataSources.News.DI.News.NewsDataSourceModule
import com.example.data.Local.DataBase.DI.DataBaseModule
import com.example.data.Remote.DI.NetworkModule
import com.example.data.Repositories.DI.News.NewsRepositoryModule
import com.example.data.Utils.DI.NetworkAvailabilityModule
import org.koin.dsl.module

val appModule = module {
    includes(
        NetworkModule,
        DataBaseModule,
        NewsRepositoryModule,
        NewsDataSourceModule,
        NetworkAvailabilityModule,

    )
}