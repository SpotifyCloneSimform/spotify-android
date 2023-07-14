package com.simformsolutions.myspotify.data.service

import com.simformsolutions.myspotify.data.model.remote.PlaylistItems
import com.simformsolutions.myspotify.data.model.remote.HomeAlbumItems
import com.simformsolutions.myspotify.data.model.remote.MediaItems
import com.simformsolutions.myspotify.data.model.remote.Playlist
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {

    @GET("me/playlists")
    suspend fun getPlaylist(): Response<MediaItems<Playlist>>

    @GET("me/albums")
    suspend fun getSongAlbums(): Response<MediaItems<HomeAlbumItems>>

    @GET("browse/featured-playlists")
    suspend fun getFeaturedPlaylist(): Response<PlaylistItems>
}