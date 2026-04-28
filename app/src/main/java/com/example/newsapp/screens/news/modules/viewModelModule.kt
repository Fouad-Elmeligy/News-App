package com.example.newsapp.screens.news.modules

import com.example.newsapp.screens.news.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::NewsViewModel)
}