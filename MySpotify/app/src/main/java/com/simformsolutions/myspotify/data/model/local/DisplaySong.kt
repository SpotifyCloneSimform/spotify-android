package com.simformsolutions.myspotify.data.model.local

data class DisplaySong(
    val type: ItemType?,
    val images: String?,
    val name: String?,
    val owner: String?,
    val data: List<DisplaySongData>?
)

data class DisplaySongData(
    val songName: String?,
    val artistsName: String?,
    val image: String?,
    val type: LibraryItemType,
    var id: String? = null,
    var songDuration: Int? = null,
    var title: String? = null,
    var subTitle: String? = null,
)

data class DisplayAlbumFooterView(
    val artistId: String = "",
    val releaseDate: String = "",
    val totalSongs: Int = 0,
    val copyRight: String = "" ,
    val artistImage: String = ""
)

enum class ItemType {
    PLAYLIST,
    ALBUM,
    ARTIST,
    PODCAST,
}