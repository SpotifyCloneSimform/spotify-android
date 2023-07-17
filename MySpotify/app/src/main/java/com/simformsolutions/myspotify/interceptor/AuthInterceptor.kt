package com.simformsolutions.myspotify.interceptor

import com.simformsolutions.myspotify.utils.AppConstants
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!chain.request().url.toString().startsWith(AppConstants.BASE_AUTH_URL)) {
            return chain.proceed(chain.request())
        }

        val token = Credentials.basic(AppConstants.CLIENT_ID, AppConstants.CLIENT_SECRET)
        val request = chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build()

        return chain.proceed(request)
    }
}