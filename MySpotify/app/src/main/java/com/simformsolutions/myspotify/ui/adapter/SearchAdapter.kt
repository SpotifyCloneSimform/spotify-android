package com.simformsolutions.myspotify.ui.adapter

import androidx.databinding.ViewDataBinding
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.SearchItem
import com.simformsolutions.myspotify.data.model.remote.Category
import com.simformsolutions.myspotify.databinding.ItemCategoryBinding
import com.simformsolutions.myspotify.databinding.ItemSearchHistoryBinding
import com.simformsolutions.myspotify.extentions.randomColor
import com.simformsolutions.myspotify.ui.base.BaseAdapter

class SearchAdapter : BaseAdapter<SearchItem>() {

    override fun getLayoutId(viewType: Int): Int = R.layout.item_search_history

    override fun setDataForListItemWithPosition(
        binding: ViewDataBinding,
        data: SearchItem,
        adapterPosition: Int
    ) {
        (binding as? ItemSearchHistoryBinding)?.apply {
            searchItem = data
        }
    }
}