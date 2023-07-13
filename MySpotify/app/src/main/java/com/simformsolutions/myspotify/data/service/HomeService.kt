package com.simformsolutions.myspotify.data.service

import com.simformsolutions.myspotify.data.model.remote.HomeAlbum
import com.simformsolutions.myspotify.data.model.remote.HomeFeaturedPlaylist
import com.simformsolutions.myspotify.data.model.remote.HomePlaylist
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {

    @GET("me/playlists")
    suspend fun getPlaylist(): Response<HomePlaylist>

    @GET("me/albums")
    suspend fun getSongAlbums(): Response<HomeAlbum>

    @GET("browse/featured-playlists")
    suspend fun getFeaturedPlaylist(): Response<HomeFeaturedPlaylist>
}