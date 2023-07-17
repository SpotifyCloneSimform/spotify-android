package com.simformsolutions.myspotify.data.service

import com.simformsolutions.myspotify.data.model.remote.Episode
import com.simformsolutions.myspotify.data.model.remote.MediaItems
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ViewEpisodesService {

    @GET("shows/{id}/episodes?limit=49")
    suspend fun getEpisodes(@Path("id") id: String): Response<MediaItems<Episode>>
}