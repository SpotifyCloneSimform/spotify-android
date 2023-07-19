package com.simformsolutions.myspotify.data.model.remote

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("album_type") val albumType: String,
    @SerializedName("artists") val artists: List<Artist>,
    @SerializedName("available_markets") val availableMarkets: List<String>,
    @SerializedName("external_urls") val externalUrls: ExternalUrl,
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: String,
    @SerializedName("images") val images: List<Image>,
    @SerializedName("name") val name: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("release_date_precision") val releaseDatePrecision: String,
    @SerializedName("total_tracks") val totalTracks: Int,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String,
    @SerializedName("tracks") val tracks: MediaItems<Track>
)