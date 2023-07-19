package com.simformsolutions.myspotify.data.model.remote

import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("country") val country: String,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("email") val email: String,
    @SerializedName("explicit_content") val explicitContent: ExplicitContent,
    @SerializedName("external_urls") val externalUrls: ExternalUrl,
    @SerializedName("followers") val followers: Followers,
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: String,
    @SerializedName("images") val images: List<Image>,
    @SerializedName("product") val product: String,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String
)