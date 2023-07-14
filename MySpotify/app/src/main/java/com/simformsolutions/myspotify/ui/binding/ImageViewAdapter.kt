package com.simformsolutions.myspotify.ui.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.simformsolutions.myspotify.R

@BindingAdapter("imageUrl", "placeholder", requireAll = false)
fun ImageView.bindImage(
    url: String?,
    placeholder: Drawable?
) {
    if (url == null) return

    Glide.with(this)
        .load(url)
        .placeholder(
            placeholder ?: ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_placeholder_24,
                context.theme
            )
        )
        .into(this)
}