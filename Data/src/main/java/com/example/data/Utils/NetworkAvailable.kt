package com.example.data.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkAvailable ( private val context: Context) {
    fun isNetworkAvailable(): Boolean{
        val connectivityManager =  context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork?:return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network)?:return false
return when{
    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
else->false
}
    }
}