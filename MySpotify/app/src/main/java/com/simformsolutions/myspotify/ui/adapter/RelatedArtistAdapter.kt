package com.simformsolutions.myspotify.ui.adapter

import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.remote.Artist
import com.simformsolutions.myspotify.databinding.ItemArtistRelatedArtistBinding
import com.simformsolutions.myspotify.ui.base.BaseAdapter
import com.simformsolutions.myspotify.ui.binding.bindImage

class RelatedArtistAdapter : BaseAdapter<Artist>() {
    override fun getLayoutId(viewType: Int) = R.layout.item_artist_related_artist

    override fun setDataForListItemWithPosition(
        binding: ViewDataBinding, data: Artist, adapterPosition: Int
    ) {
        (binding as? ItemArtistRelatedArtistBinding)?.apply {
            binding.artist = data
            binding.thumbnail.bindImage(
                data.images.firstOrNull()?.url, ResourcesCompat.getDrawable(
                    binding.root.resources, R.drawable.ic_artist_24, binding.root.context.theme
                ), true
            )
        }
    }
}