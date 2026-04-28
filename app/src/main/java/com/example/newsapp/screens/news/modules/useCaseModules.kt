package com.example.newsapp.screens.news.modules

import com.example.domain.UseCases.News.GetNewsBySourceUseCase
import com.example.domain.UseCases.News.GetSourcesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetSourcesUseCase)
    factoryOf(::GetNewsBySourceUseCase)
}