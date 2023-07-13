package com.simformsolutions.myspotify.data.service

import com.simformsolutions.myspotify.data.model.remote.Album
import com.simformsolutions.myspotify.data.model.remote.AlbumArtist
import com.simformsolutions.myspotify.data.model.remote.Artist
import com.simformsolutions.myspotify.data.model.remote.Playlist
import com.simformsolutions.myspotify.data.model.remote.ViewAlbum
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ViewPlaylistService {

    @GET("playlists/{id}")
    suspend fun getPlaylistItems(@Path("id") id: String): Response<Playlist>

    @GET("albums/{id}")
    suspend fun getAlbumSongs(@Path("id") id: String): Response<ViewAlbum>

    @GET("artists/{id}")
    suspend fun getArtist(@Path("id") id: String): Response<AlbumArtist>
}