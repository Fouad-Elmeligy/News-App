package com.example.data.Remote.DI

import android.util.Log
import com.example.data.Remote.API.NewsService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Log.e("API", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newUrl = chain.request().url.newBuilder()
            val newRequest = chain.request().newBuilder()
            newUrl.addQueryParameter("apiKey", "177f7f7acb824932b1b50ae431bcb5da")
            newRequest.url(newUrl.build())
            chain.proceed(newRequest.build())
        }
    }


    @Provides
    @Singleton
    fun providesOKHttpClint(
        loggingInterceptor: HttpLoggingInterceptor,
        apiKeyInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor).build()
    }

    @Provides
    @Singleton
    fun providesRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(gsonConverterFactory).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun providesNewsService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }
}