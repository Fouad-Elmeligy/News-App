package com.example.newsapp

import android.app.Application
import android.content.Context
import com.example.newsapp.DataBase.NewsAppDataBase

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initDataBase()
    }

    private fun initDataBase() {
        NewsAppDataBase.initDataBase(this)
    }
}