package com.simformsolutions.myspotify.di

import android.content.Context
import com.simformsolutions.myspotify.helper.PreferenceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesPreferenceHelper(@ApplicationContext context: Context) = PreferenceHelper(context)
}