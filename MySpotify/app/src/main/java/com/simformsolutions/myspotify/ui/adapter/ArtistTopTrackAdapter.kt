package com.simformsolutions.myspotify.ui.adapter

import androidx.databinding.ViewDataBinding
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.DisplaySongData
import com.simformsolutions.myspotify.databinding.ItemArtistTopTracksBinding
import com.simformsolutions.myspotify.ui.base.BaseAdapter

class ArtistTopTrackAdapter: BaseAdapter<DisplaySongData>() {

    override fun getLayoutId(viewType: Int) = R.layout.item_artist_top_tracks

    override fun setDataForListItemWithPosition(
        binding: ViewDataBinding,
        data: DisplaySongData,
        adapterPosition: Int
    ) {
        (binding as? ItemArtistTopTracksBinding)?.let {
            binding.song = data
        }
    }

}