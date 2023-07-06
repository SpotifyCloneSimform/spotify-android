package com.simformsolutions.myspotify.listener

import androidx.recyclerview.widget.RecyclerView

/**
 * Item click listener for [RecyclerView]
 */
interface ItemClickListener<T> {

    /**
     * On click method for item click.
     *
     * @param item The data item
     * @param position Adapter position on which clicked
     */
    fun onClick(item: T, position: Int)
}