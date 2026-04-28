package com.example.data.Repositories.DI.News


import com.example.data.Repositories.NewsRepository.NewsRepositoryImpl
import com.example.domain.Repositories.News.NewsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val NewsRepositoryModule = module {
   singleOf(::NewsRepositoryImpl) bind NewsRepository::class
}