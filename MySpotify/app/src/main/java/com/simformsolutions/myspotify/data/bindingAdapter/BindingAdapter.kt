package com.simformsolutions.myspotify.data.bindingAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.simformsolutions.myspotify.R

@BindingAdapter("imageUrl")
fun ImageView.bindImage(url: String?) {
    if (url == null) return

    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.albums)
        .into(this)
}