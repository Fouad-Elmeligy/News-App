package com.example.newsapp

import android.app.Application
import com.example.data.appModule.appModule
import com.example.newsapp.screens.news.modules.useCaseModule
import com.example.newsapp.screens.news.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(
                appModule,
                viewModelModule,
                useCaseModule
            )
        }
    }
}