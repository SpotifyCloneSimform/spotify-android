package com.simformsolutions.myspotify.ui.adapter

import androidx.databinding.ViewDataBinding
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.DisplaySongData
import com.simformsolutions.myspotify.databinding.ItemPlaylistSongBinding
import com.simformsolutions.myspotify.ui.base.BaseAdapter

class ViewPlaylistSongsAdapter : BaseAdapter<DisplaySongData>() {

    override fun getLayoutId(viewType: Int): Int = R.layout.item_playlist_song

    override fun setDataForListItemWithPosition(
        binding: ViewDataBinding,
        data: DisplaySongData,
        adapterPosition: Int
    ) {
        (binding as? ItemPlaylistSongBinding)?.apply {
            song = data
        }
    }
}