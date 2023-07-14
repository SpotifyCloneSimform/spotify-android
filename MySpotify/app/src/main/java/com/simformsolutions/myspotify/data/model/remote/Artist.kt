package com.simformsolutions.myspotify.data.model.remote

import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("external_urls") val externalUrls: ExternalUrl,
    @SerializedName("followers") val followers: Followers,
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String
)