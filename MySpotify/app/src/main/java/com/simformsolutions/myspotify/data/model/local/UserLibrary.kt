package com.simformsolutions.myspotify.data.model.local

import android.content.Context
import com.simformsolutions.myspotify.R

data class LibraryDisplay(
    val isFiltered: Boolean,
    val name: String?,
    val ownerDisplayName: String?,
    val image: String?,
    val type: LibraryItemType,
    val id: String?,
    var description: String = ""
)

enum class LibraryItemType(val value: Int) {
    PLAYLIST(0),
    ALBUM(1),
    ARTISTS(2),
    PODCAST(3);


    fun getLocalizedName(context: Context): String =
        context.getString(
            when (this) {
                PLAYLIST -> R.string.playlist
                ALBUM -> R.string.album
                ARTISTS -> R.string.artist
                PODCAST -> R.string.podcast
            }
        )
}