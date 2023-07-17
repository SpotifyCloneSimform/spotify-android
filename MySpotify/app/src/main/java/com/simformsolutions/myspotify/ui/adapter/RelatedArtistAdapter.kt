package com.simformsolutions.myspotify.ui.adapter

import androidx.databinding.ViewDataBinding
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.bindingAdapter.bindImage
import com.simformsolutions.myspotify.data.model.remote.Artist
import com.simformsolutions.myspotify.databinding.ItemArtistRelatedArtistBinding
import com.simformsolutions.myspotify.ui.base.BaseAdapter

class RelatedArtistAdapter: BaseAdapter<Artist>() {
    override fun getLayoutId(viewType: Int) = R.layout.item_artist_related_artist

    override fun setDataForListItemWithPosition(
        binding: ViewDataBinding,
        data: Artist,
        adapterPosition: Int
    ) {
        (binding as? ItemArtistRelatedArtistBinding)?.apply {
            binding.artist = data
            binding.thumbnail.bindImage(data.images.firstOrNull()?.url, isRounded = true)
        }
    }

}