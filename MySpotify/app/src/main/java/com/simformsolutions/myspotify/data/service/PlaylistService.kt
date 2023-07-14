package com.simformsolutions.myspotify.data.service

import com.simformsolutions.myspotify.data.model.remote.PlaylistItems
import com.simformsolutions.myspotify.data.model.remote.MediaItems
import com.simformsolutions.myspotify.data.model.remote.Playlist
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaylistService {

    @GET("me/playlists")
    suspend fun getAuthorizedUserPlaylists(
        @Query("limit") limit: Int = 50, @Query("offset") offset: Int = 0
    ): Response<MediaItems<Playlist>>

    @GET("playlists/{playlistId}")
    suspend fun getPlaylist(
        @Path("playlistId") playlistId: String,
        @Query("fields") fields: String? = null
    ): Response<Playlist>

    @GET("browse/categories/{categoryId}/playlists")
    suspend fun getCategoryPlaylists(
        @Path("categoryId") categoryId: String,
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): Response<PlaylistItems>
}