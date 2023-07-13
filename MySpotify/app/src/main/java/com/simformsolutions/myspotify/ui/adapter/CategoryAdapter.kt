package com.simformsolutions.myspotify.ui.adapter

import androidx.databinding.ViewDataBinding
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.remote.Category
import com.simformsolutions.myspotify.databinding.ItemCategoryBinding
import com.simformsolutions.myspotify.extentions.randomColor
import com.simformsolutions.myspotify.ui.base.BaseAdapter

class CategoryAdapter : BaseAdapter<Category>() {

    override fun getLayoutId(viewType: Int): Int = R.layout.item_category

    override fun setDataForListItemWithPosition(
        binding: ViewDataBinding,
        data: Category,
        adapterPosition: Int
    ) {
        (binding as? ItemCategoryBinding)?.apply {
            category = data
            categoryContainer.setCardBackgroundColor(Int.randomColor())
        }
    }
}