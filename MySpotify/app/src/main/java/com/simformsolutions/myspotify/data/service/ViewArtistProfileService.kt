package com.simformsolutions.myspotify.data.service

import com.simformsolutions.myspotify.data.model.remote.Artist
import com.simformsolutions.myspotify.data.model.remote.RelatedArtist
import com.simformsolutions.myspotify.data.model.remote.TopTracks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ViewArtistProfileService {

    @GET("artists/{id}")
    suspend fun getArtistProfile(@Path("id") id: String): Response<Artist>

    @GET("artists/{id}/top-tracks?market=IN")
    suspend fun getArtistTopTracks(@Path("id") id: String): Response<TopTracks>

    @GET("artists/{id}/related-artists")
    suspend fun getRelatedArtists(@Path("id") id: String): Response<RelatedArtist>
}