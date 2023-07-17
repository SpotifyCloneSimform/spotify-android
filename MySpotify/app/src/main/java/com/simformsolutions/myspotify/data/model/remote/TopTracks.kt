package com.simformsolutions.myspotify.data.model.remote

import com.google.gson.annotations.SerializedName

data class TopTracks(
    @SerializedName("tracks") val track: List<Track>
)