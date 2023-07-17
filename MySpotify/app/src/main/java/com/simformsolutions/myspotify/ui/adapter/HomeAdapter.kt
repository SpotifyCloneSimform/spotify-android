package com.simformsolutions.myspotify.ui.adapter

import androidx.databinding.ViewDataBinding
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.HomeData
import com.simformsolutions.myspotify.data.model.local.HomeDisplayData
import com.simformsolutions.myspotify.databinding.ItemHomeRvBinding
import com.simformsolutions.myspotify.listener.ItemClickListener
import com.simformsolutions.myspotify.ui.base.BaseAdapter

class HomeAdapter(private val onItemClick: (HomeDisplayData) -> Unit ) : BaseAdapter<HomeData>() {

    override fun getLayoutId(viewType: Int): Int = R.layout.item_home_rv

    override fun setDataForListItemWithPosition(
        binding: ViewDataBinding,
        data: HomeData,
        adapterPosition: Int
    ) {
        (binding as? ItemHomeRvBinding)?.apply {
            val adapter = HomeItemAdapter()
            title = data.sectionName
            rvHomeData.adapter = adapter
            adapter.submitList(data.homeDisplayData)
            adapter.itemClickListener = object :ItemClickListener<HomeDisplayData> {
                override fun onClick(item: HomeDisplayData, position: Int) {
                    onItemClick(item)
                }
            }
        }
    }
}