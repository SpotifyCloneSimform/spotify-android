package com.simformsolutions.myspotify.data.model.remote

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("tracks") val tracks: MediaItems<Track>?,
    @SerializedName("artists") val artists: MediaItems<Artist>?,
    @SerializedName("albums") val albums: MediaItems<Album>?,
    @SerializedName("playlists") val playlists: MediaItems<Playlist>?
)