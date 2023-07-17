package com.simformsolutions.myspotify.data.service

import com.simformsolutions.myspotify.data.model.remote.Track
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TrackService {

    @GET("tracks/{trackId}")
    suspend fun getTrackDetail(@Path("trackId") trackId: String): Response<Track>

    @GET("me/tracks/contains")
    suspend fun checkLike(@Query("ids") ids: List<String>): Response<List<Boolean>>

    @PUT("me/tracks")
    suspend fun likeTrack(@Query("ids") ids: List<String>): Response<Unit>

    @DELETE("me/tracks")
    suspend fun removeLike(@Query("ids") ids: List<String>): Response<Unit>
}