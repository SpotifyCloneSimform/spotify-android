package com.simformsolutions.myspotify.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.simformsolutions.myspotify.BuildConfig
import com.simformsolutions.myspotify.data.repository.AlbumRepository
import com.simformsolutions.myspotify.data.repository.ArtistProfileRepository
import com.simformsolutions.myspotify.data.repository.AuthRepository
import com.simformsolutions.myspotify.data.repository.HomeRepository
import com.simformsolutions.myspotify.data.repository.PlaylistRepository
import com.simformsolutions.myspotify.data.repository.SearchRepository
import com.simformsolutions.myspotify.data.repository.TrackRepository
import com.simformsolutions.myspotify.data.repository.UserLibraryRepository
import com.simformsolutions.myspotify.data.repository.UserRepository
import com.simformsolutions.myspotify.data.repository.ViewEpisodeRepository
import com.simformsolutions.myspotify.data.repository.ViewPlaylistRepository
import com.simformsolutions.myspotify.data.service.AlbumService
import com.simformsolutions.myspotify.data.service.AuthService
import com.simformsolutions.myspotify.data.service.HomeService
import com.simformsolutions.myspotify.data.service.PlaylistService
import com.simformsolutions.myspotify.data.service.SearchService
import com.simformsolutions.myspotify.data.service.TrackService
import com.simformsolutions.myspotify.data.service.UserLibraryService
import com.simformsolutions.myspotify.data.service.UserService
import com.simformsolutions.myspotify.data.service.ViewArtistProfileService
import com.simformsolutions.myspotify.data.service.ViewEpisodesService
import com.simformsolutions.myspotify.data.service.ViewPlaylistService
import com.simformsolutions.myspotify.helper.PreferenceHelper
import com.simformsolutions.myspotify.interceptor.ApiAuthenticator
import com.simformsolutions.myspotify.interceptor.AuthInterceptor
import com.simformsolutions.myspotify.utils.AppConstants
import dagger.Lazy
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
    fun providesAuthInterceptor(): AuthInterceptor =
        AuthInterceptor()

    @Provides
    @Singleton
    fun providesApiAuthenticator(
        authRepository: Lazy<AuthRepository>,
        preferenceHelper: PreferenceHelper
    ): ApiAuthenticator =
        ApiAuthenticator(authRepository, preferenceHelper)

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
        authInterceptor: AuthInterceptor,
        apiAuthenticator: ApiAuthenticator
    ): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(apiAuthenticator)
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
            .baseUrl(AppConstants.BASE_API_URL + AppConstants.VERSION)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Singleton
    @Provides
    fun providesAuthService(@Named(AUTH_RETROFIT) retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Singleton
    @Provides
    fun providesSearchService(@Named(API_RETROFIT) retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

    @Singleton
    @Provides
    fun providesUserService(@Named(API_RETROFIT) retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun providesPlaylistService(@Named(API_RETROFIT) retrofit: Retrofit): PlaylistService =
        retrofit.create(PlaylistService::class.java)

    @Singleton
    @Provides
    fun providesUserLibraryService(@Named(API_RETROFIT) retrofit: Retrofit): UserLibraryService =
        retrofit.create(UserLibraryService::class.java)

    @Singleton
    @Provides
    fun providesHomeService(@Named(API_RETROFIT) retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @Singleton
    @Provides
    fun providesViewPlaylistService(@Named(API_RETROFIT) retrofit: Retrofit): ViewPlaylistService =
        retrofit.create(ViewPlaylistService::class.java)

    @Singleton
    @Provides
    fun providesArtistProfileService(@Named(API_RETROFIT) retrofit: Retrofit): ViewArtistProfileService =
        retrofit.create(ViewArtistProfileService::class.java)

    @Singleton
    @Provides
    fun providesViewEpisodesService(@Named(API_RETROFIT) retrofit: Retrofit): ViewEpisodesService =
        retrofit.create(ViewEpisodesService::class.java)

    @Singleton
    @Provides
    fun providesTrackService(@Named(API_RETROFIT) retrofit: Retrofit): TrackService =
        retrofit.create(TrackService::class.java)

    @Singleton
    @Provides
    fun providesAlbumService(@Named(API_RETROFIT) retrofit: Retrofit): AlbumService =
        retrofit.create(AlbumService::class.java)

    @Singleton
    @Provides
    fun providesAuthRepository(authService: AuthService): AuthRepository =
        AuthRepository(authService)

    @Singleton
    @Provides
    fun providesSearchRepository(searchService: SearchService): SearchRepository =
        SearchRepository(searchService)

    @Singleton
    @Provides
    fun providesUserRepository(userService: UserService): UserRepository =
        UserRepository(userService)

    @Singleton
    @Provides
    fun providesPlaylistRepository(playlistService: PlaylistService): PlaylistRepository =
        PlaylistRepository(playlistService)

    @Singleton
    @Provides
    fun providesHomeRepository(homeService: HomeService): HomeRepository =
        HomeRepository(homeService)

    @Singleton
    @Provides
    fun providesViewPlaylistRepository(viewPlaylistService: ViewPlaylistService): ViewPlaylistRepository =
        ViewPlaylistRepository(viewPlaylistService)


    @Singleton
    @Provides
    fun providesUserLibraryRepository(userLibraryService: UserLibraryService): UserLibraryRepository =
        UserLibraryRepository(userLibraryService)

    @Singleton
    @Provides
    fun providesArtistProfileRepository(artistProfileService: ViewArtistProfileService): ArtistProfileRepository =
        ArtistProfileRepository(artistProfileService)

    @Singleton
    @Provides
    fun providesViewEpisodeRepository(artistProfileService: ViewEpisodesService): ViewEpisodeRepository =
        ViewEpisodeRepository(artistProfileService)

    @Singleton
    @Provides
    fun providesTrackRepository(trackService: TrackService): TrackRepository =
        TrackRepository(trackService)

    @Singleton
    @Provides
    fun providesAlbumRepository(albumService: AlbumService): AlbumRepository =
        AlbumRepository(albumService)
}