package com.simformsolutions.myspotify.data.model.remote

import com.google.gson.annotations.SerializedName

data class Shows (
    @SerializedName("added_at") var addedAt: String?,
    @SerializedName("show") var show: Show?
)

data class Show (
    @SerializedName("available_markets") var availableMarkets: ArrayList<String>,
    @SerializedName("copyrights") var copyrights: ArrayList<String>,
    @SerializedName("description") var description: String?,
    @SerializedName("explicit") var explicit: Boolean?,
    @SerializedName("external_urls") var externalUrls: ExternalUrl?,
    @SerializedName("href") var href: String?,
    @SerializedName("html_description") var htmlDescription: String?,
    @SerializedName("id") var id: String?,
    @SerializedName("images") var images: ArrayList<Image>,
    @SerializedName("is_externally_hosted") var isExternallyHosted: Boolean?,
    @SerializedName("languages") var languages: ArrayList<String>,
    @SerializedName("media_type") var mediaType: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("publisher") var publisher: String?,
    @SerializedName("total_episodes") var totalEpisodes: Int?,
    @SerializedName("type") var type: String?,
    @SerializedName("uri") var uri: String,
)