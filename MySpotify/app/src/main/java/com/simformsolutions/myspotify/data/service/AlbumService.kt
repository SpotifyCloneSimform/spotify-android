package com.simformsolutions.myspotify.data.service

import com.simformsolutions.myspotify.data.model.remote.Album
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumService {

    @GET("albums/{albumId}")
    suspend fun getAlbum(
        @Path("albumId") albumId: String
    ): Response<Album>
}