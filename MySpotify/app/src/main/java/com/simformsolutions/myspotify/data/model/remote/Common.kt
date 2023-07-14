package com.simformsolutions.myspotify.data.model.remote

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("height") val height: Int?,
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int?
)

data class ExternalUrl(
    @SerializedName("spotify") val spotify: String
)

data class ExternalId(
    @SerializedName("isrc") val isrc: String
)

data class VideoThumbnail(
    @SerializedName("url") val url: String?
)

data class Followers(
    @SerializedName("href") val href: String?,
    @SerializedName("total") val total: Int
)

data class ExplicitContent(
    @SerializedName("filter_enabled") val filterEnabled: Boolean,
    @SerializedName("filter_locked") val filterLocked: Boolean
)

data class CopyRights(
    @SerializedName("text") val text: String?, @SerializedName("type") val type: String?
)