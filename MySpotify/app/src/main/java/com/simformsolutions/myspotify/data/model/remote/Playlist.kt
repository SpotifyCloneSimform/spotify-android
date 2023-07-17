package com.simformsolutions.myspotify.data.model.remote


import com.google.gson.annotations.SerializedName

data class Playlist(
    @SerializedName("collaborative") val collaborative: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("external_urls") val externalUrls: ExternalUrl,
    @SerializedName("followers") val followers: Followers,
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: String,
    @SerializedName("images") val images: List<Image>,
    @SerializedName("name") val name: String,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("primary_color") val primaryColor: String?,
    @SerializedName("public") val `public`: Boolean,
    @SerializedName("snapshot_id") val snapshotId: String,
    @SerializedName("tracks") val tracks: MediaItems<PlaylistTracksItems>,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String
)

data class PlaylistItems(
    @SerializedName("message") val message: String,
    @SerializedName("playlists") val playlists: MediaItems<Playlist>
)

data class PlaylistTracksItems (
    @SerializedName("added_at") val addedAt: String?,
    @SerializedName("track") val track: Track?
)