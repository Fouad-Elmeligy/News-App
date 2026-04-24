package com.example.data.Utils.DI

import android.content.Context
import android.net.ConnectivityManager
import com.example.data.Utils.NetworkAvailable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object NetworkAvailabilityModule {
    @Provides
    @Singleton
    fun providesNetworkAvailable(
        @ApplicationContext context: Context
    ): NetworkAvailable {
        return NetworkAvailable(context)
    }
}