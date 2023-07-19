package com.simformsolutions.myspotify.data.model.local

import android.content.Context
import com.simformsolutions.myspotify.R

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
    val copyRight: String = "",
    val artistImage: String = ""
)

enum class ItemType {
    PLAYLIST,
    ALBUM,
    TRACK,
    ARTIST,
    PODCAST;

    fun getLocalizedName(context: Context): String =
        context.getString(
            when (this) {
                PLAYLIST -> R.string.playlist
                ALBUM -> R.string.album
                TRACK -> R.string.track
                ARTIST -> R.string.artist
                PODCAST -> R.string.podcast
            }
        )
}