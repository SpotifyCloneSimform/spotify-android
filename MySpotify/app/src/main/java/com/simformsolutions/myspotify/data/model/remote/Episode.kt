package com.simformsolutions.myspotify.data.model.remote

import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("audio_preview_url") var audioPreviewUrl: String,
    @SerializedName("description") var description: String,
    @SerializedName("duration_ms") var durationMs: Int,
    @SerializedName("explicit") var explicit: Boolean,
    @SerializedName("external_urls") var externalUrls: ExternalUrl,
    @SerializedName("href") var href: String,
    @SerializedName("html_description") var htmlDescription: String,
    @SerializedName("id") var id: String,
    @SerializedName("images") var images: ArrayList<Image>,
    @SerializedName("is_externally_hosted") var isExternallyHosted: Boolean,
    @SerializedName("is_playable") var isPlayable: Boolean,
    @SerializedName("languages") var languages: ArrayList<String> = arrayListOf(),
    @SerializedName("name") var name: String,
    @SerializedName("release_date") var releaseDate: String,
    @SerializedName("release_date_precision") var releaseDatePrecision: String,
    @SerializedName("type") var type: String,
    @SerializedName("uri") var uri: String
)