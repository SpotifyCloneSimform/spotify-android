package com.simformsolutions.myspotify.data.model.remote

import com.google.gson.annotations.SerializedName

data class RelatedArtist (
    @SerializedName("artists") val artist: List<Artist>
)