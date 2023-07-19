package com.simformsolutions.myspotify.ui.adapter

import androidx.databinding.ViewDataBinding
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.LibraryDisplay
import com.simformsolutions.myspotify.data.model.local.LibraryItemType
import com.simformsolutions.myspotify.databinding.ItemUserLibraryArtistsBinding
import com.simformsolutions.myspotify.databinding.ItemUserLibraryBinding
import com.simformsolutions.myspotify.ui.base.BaseAdapter


class UserLibraryAdapter : BaseAdapter<LibraryDisplay>() {

    override fun getItemViewType(position: Int): Int = itemList[position].type.ordinal

    override fun getLayoutId(viewType: Int): Int = when (LibraryItemType.values()[viewType]) {
        LibraryItemType.ALBUM, LibraryItemType.PLAYLIST, LibraryItemType.PODCAST -> R.layout.item_user_library
        LibraryItemType.ARTISTS -> R.layout.item_user_library_artists
    }

    override fun setDataForListItemWithPosition(
        binding: ViewDataBinding,
        data: LibraryDisplay,
        adapterPosition: Int
    ) {
        when (binding) {

            is ItemUserLibraryBinding -> {
                binding.libraryData = data
                if (!data.isFiltered) {
                    if (data.type == LibraryItemType.PLAYLIST) {
                        binding.tvOwnerName.text = "Playlist • ${data.ownerDisplayName}"
                    } else {
                        binding.tvOwnerName.text = "Album • ${data.ownerDisplayName}"
                    }
                } else {
                    binding.tvOwnerName.text = data.ownerDisplayName
                }
            }

            is ItemUserLibraryArtistsBinding -> {
                binding.libraryData = data
                if (!data.isFiltered) {
                    binding.tvArtistName.text = "${data.name}"
                } else {
                    binding.tvArtistName.text = data.name
                }
            }
        }
    }

    fun addItems(list: List<LibraryDisplay>) {
        val count = itemList.count()
        itemList.addAll(list)
        notifyItemRangeInserted(count, list.count())
    }
}