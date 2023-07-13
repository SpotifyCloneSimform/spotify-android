package com.simformsolutions.myspotify.data.model.remote

import com.google.gson.annotations.SerializedName

data class HomePlaylist(
    @SerializedName("href") var href: String? = null,
    @SerializedName("items") var items: List<Items> = arrayListOf()
)

data class ExternalUrls(
    @SerializedName("spotify") var spotify: String? = null
)

data class Images(
    @SerializedName("height") var height: Int? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("width") var width: Int? = null
)

data class Owner(
    @SerializedName("display_name") var displayName: String? = null,
    @SerializedName("external_urls") var externalUrls: ExternalUrls? = ExternalUrls(),
    @SerializedName("href") var href: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("uri") var uri: String? = null
)

data class Tracks(
    @SerializedName("href") var href: String? = null,
    @SerializedName("total") var total: Int? = null

)

data class Items(
    @SerializedName("collaborative") var collaborative: Boolean? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("external_urls") var externalUrls: ExternalUrls? = ExternalUrls(),
    @SerializedName("href") var href: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("images") var images: ArrayList<Images> = arrayListOf(),
    @SerializedName("name") var name: String? = null,
    @SerializedName("owner") var owner: Owner? = Owner(),
    @SerializedName("primary_color") var primaryColor: String? = null,
    @SerializedName("public") var public: Boolean? = null,
    @SerializedName("snapshot_id") var snapshotId: String? = null,
    @SerializedName("tracks") var tracks: Tracks? = Tracks(),
    @SerializedName("type") var type: String? = null,
    @SerializedName("uri") var uri: String? = null
)