package com.simformsolutions.myspotify.intercepter

import com.simformsolutions.myspotify.helper.PreferenceHelper
import com.simformsolutions.myspotify.utils.AppConstants
import com.simformsolutions.myspotify.utils.PreferenceKeys
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor(
    private val preferenceHelper: PreferenceHelper
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = if (chain.request().url.toString().startsWith(AppConstants.BASE_AUTH_URL)) {
            Credentials.basic(AppConstants.CLIENT_ID, AppConstants.CLIENT_SECRET)
        } else {
            preferenceHelper.getString(PreferenceKeys.ACCESS_TOKEN, "")
        }

        val request = chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build()

        return chain.proceed(request)
    }
}