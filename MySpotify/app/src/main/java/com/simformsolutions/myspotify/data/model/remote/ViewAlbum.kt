package com.simformsolutions.myspotify.data.model.remote

import com.google.gson.annotations.SerializedName

data class ViewAlbum(
    @SerializedName("artists") val artists: List<Artist>,
    @SerializedName("copyrights") val copyrights: List<CopyRights>,
    @SerializedName("id") val id: String,
    @SerializedName("images") val images: List<Image>,
    @SerializedName("name") val name: String,
    @SerializedName("total_tracks") val totalTracks: Int,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("tracks") val tracks: MediaItems<Track>
)
