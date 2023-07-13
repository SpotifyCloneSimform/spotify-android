package com.simformsolutions.myspotify.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.simformsolutions.myspotify.BuildConfig
import com.simformsolutions.myspotify.data.repository.AuthRepository
import com.simformsolutions.myspotify.data.repository.HomeRepository
import com.simformsolutions.myspotify.data.service.AuthService
import com.simformsolutions.myspotify.data.service.HomeService
import com.simformsolutions.myspotify.helper.PreferenceHelper
import com.simformsolutions.myspotify.intercepter.ApiInterceptor
import com.simformsolutions.myspotify.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val AUTH_RETROFIT = "AUTH_RETROFIT"
    private const val API_RETROFIT = "API_RETROFIT"
    private const val NETWORK_TIMEOUT: Long = 60 // Second

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    @Provides
    @Singleton
    fun providesApiInterceptor(preferenceHelper: PreferenceHelper): ApiInterceptor = ApiInterceptor(preferenceHelper)

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        apiIInterceptor: ApiInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiIInterceptor)
            .build()

    @Provides
    @Singleton
    @Named(AUTH_RETROFIT)
    fun providesAuthRetrofit(gson: Gson, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_AUTH_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    @Named(API_RETROFIT)
    fun providesApiRetrofit(gson: Gson, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_API_URL+AppConstants.VERSION)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Singleton
    @Provides
    fun providesAuthService(@Named(AUTH_RETROFIT) retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Singleton
    @Provides
    fun providesAuthRepository(authService: AuthService): AuthRepository =
        AuthRepository(authService)

    @Singleton
    @Provides
    fun providesHomeService(@Named(API_RETROFIT) retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @Singleton
    @Provides
    fun providesHomeRepository(homeService: HomeService): HomeRepository =
        HomeRepository(homeService)


}