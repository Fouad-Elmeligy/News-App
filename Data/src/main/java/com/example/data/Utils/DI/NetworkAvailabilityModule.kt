package com.example.data.Utils.DI

import com.example.data.Utils.NetworkAvailable
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val NetworkAvailabilityModule = module {

     single {  NetworkAvailable(androidContext())}

}