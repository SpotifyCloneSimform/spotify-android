package com.simformsolutions.myspotify.data.model.remote

data class AlbumArtist (
    val followers: ArtistFollowers?,
    val images: List<AlbumArtistImage>?,
    val id: String?,
    val name: String?,
)

data class ArtistFollowers (
    val href: String?,
    val total: Int,
)

data class AlbumArtistImage (
    val height: Int?,
    val url: String?,
    val width: Int?,
)
