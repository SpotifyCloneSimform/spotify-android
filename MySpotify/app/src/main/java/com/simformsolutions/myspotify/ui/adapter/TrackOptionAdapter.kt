package com.simformsolutions.myspotify.ui.adapter

import androidx.databinding.ViewDataBinding
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.SearchItem
import com.simformsolutions.myspotify.data.model.local.TrackOptionItem
import com.simformsolutions.myspotify.data.model.remote.Category
import com.simformsolutions.myspotify.databinding.ItemCategoryBinding
import com.simformsolutions.myspotify.databinding.ItemSearchHistoryBinding
import com.simformsolutions.myspotify.databinding.ItemTrackOptionBinding
import com.simformsolutions.myspotify.extentions.randomColor
import com.simformsolutions.myspotify.ui.base.BaseAdapter

class TrackOptionAdapter : BaseAdapter<TrackOptionItem>() {

    override fun getLayoutId(viewType: Int): Int = R.layout.item_track_option

    override fun setDataForListItemWithPosition(
        binding: ViewDataBinding,
        data: TrackOptionItem,
        adapterPosition: Int
    ) {
        (binding as? ItemTrackOptionBinding)?.apply {
            trackOption = data
        }
    }
}