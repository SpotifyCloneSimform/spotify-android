package com.simformsolutions.myspotify.ui.adapter

import androidx.databinding.ViewDataBinding
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.ProfileMediaItem
import com.simformsolutions.myspotify.databinding.ItemProfileMediaBinding
import com.simformsolutions.myspotify.ui.base.BaseAdapter

class ProfileMediaAdapter : BaseAdapter<ProfileMediaItem>() {

    override fun getLayoutId(viewType: Int): Int = R.layout.item_profile_media

    override fun setDataForListItemWithPosition(
        binding: ViewDataBinding,
        data: ProfileMediaItem,
        adapterPosition: Int
    ) {
        (binding as? ItemProfileMediaBinding)?.apply {
            mediaItem = data
        }
    }
}