package com.simformsolutions.myspotify.data.model.remote

data class HomeFeaturedPlaylist(
    val message: String?,
    val playlists: HomeFeaturedPlaylistItem?
)

data class HomeFeaturedPlaylistItem(
    val href: String?,
    var items: List<Items>?
)