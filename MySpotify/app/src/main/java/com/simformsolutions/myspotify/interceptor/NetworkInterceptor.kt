package com.simformsolutions.myspotify.interceptor

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            Log.d("netError", "No internet connection")
        }
        return chain.proceed(chain.request())
    }

    @SuppressLint("ServiceCast")
    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return listOf(NetworkCapabilities.NET_CAPABILITY_INTERNET,
            NetworkCapabilities.TRANSPORT_WIFI,
            NetworkCapabilities.TRANSPORT_ETHERNET,
            NetworkCapabilities.TRANSPORT_CELLULAR).any { capabilities?.hasCapability(it) == true }
    }
}