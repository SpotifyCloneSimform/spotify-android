package com.simformsolutions.myspotify.data.model.remote

import com.google.gson.annotations.SerializedName

data class FollowedArtists (
    @SerializedName("artists") val artists : MediaItems<Artist>,
)