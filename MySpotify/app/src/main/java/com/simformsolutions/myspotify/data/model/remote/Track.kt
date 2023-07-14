package com.simformsolutions.myspotify.data.model.remote

import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("album") val album: Album,
    @SerializedName("artists") val artists: List<Artist>,
    @SerializedName("available_markets") val availableMarkets: List<String>,
    @SerializedName("disc_number") val discNumber: Int,
    @SerializedName("duration_ms") val durationMs: Int,
    @SerializedName("explicit") val explicit: Boolean,
    @SerializedName("external_ids") val externalIds: ExternalId,
    @SerializedName("external_urls") val externalUrls: ExternalUrl,
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: String,
    @SerializedName("is_local") val isLocal: Boolean,
    @SerializedName("name") val name: String,
    @SerializedName("popularity") val popularity: Int,
    @SerializedName("preview_url") val previewUrl: String?,
    @SerializedName("track_number") val trackNumber: Int,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String
)