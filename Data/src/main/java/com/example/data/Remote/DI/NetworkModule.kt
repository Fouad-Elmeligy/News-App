package com.example.data.Remote.DI

import android.util.Log
import com.example.data.Remote.API.NewsService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val NetworkModule = module {

    single {
        HttpLoggingInterceptor { message ->
            Log.e("API", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


    single {
        GsonConverterFactory.create()
    }

    single {
        Interceptor { chain ->
            val newUrl = chain.request().url.newBuilder()
            val newRequest = chain.request().newBuilder()
            newUrl.addQueryParameter("apiKey", "177f7f7acb824932b1b50ae431bcb5da")
            newRequest.url(newUrl.build())
            chain.proceed(newRequest.build())
        }
    }


   single {
         OkHttpClient.Builder().addInterceptor(get<Interceptor>()).addInterceptor(get<HttpLoggingInterceptor>()).build()
    }


   single {

            Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
               .addConverterFactory(get<GsonConverterFactory>()).client(get<OkHttpClient>()).build()
       }



       single {  get<Retrofit>().create(NewsService::class.java)
       }
    }
