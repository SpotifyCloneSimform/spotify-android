package com.simformsolutions.myspotify.ui.adapters

import androidx.databinding.ViewDataBinding
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.remote.HomeDisplayData
import com.simformsolutions.myspotify.databinding.ItemHomeRvDataBinding
import com.simformsolutions.myspotify.ui.base.BaseAdapter

class HomeItemRVAdapter : BaseAdapter<HomeDisplayData>() {

    override fun getLayoutId(viewType: Int): Int = R.layout.item_home_rv_data

    override fun setDataForListItemWithPosition(
        binding: ViewDataBinding,
        data: HomeDisplayData,
        adapterPosition: Int
    ) {
        (binding as? ItemHomeRvDataBinding)?.apply {
            song = data
        }
    }
}