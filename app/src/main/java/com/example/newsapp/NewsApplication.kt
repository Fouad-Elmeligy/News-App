package com.example.newsapp

import android.app.Application
import com.example.data.Local.DataBase.NewsAppDataBase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication : Application() {
}