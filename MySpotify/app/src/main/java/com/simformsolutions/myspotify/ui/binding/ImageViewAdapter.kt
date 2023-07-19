package com.simformsolutions.myspotify.ui.binding

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.simformsolutions.myspotify.R

@SuppressLint("CheckResult")
@BindingAdapter("imageUrl", "placeholder", "isRounded", requireAll = false)
fun ImageView.bindImage(
    url: String?,
    placeholder: Drawable? = null,
    isRounded: Boolean? = false
) {
    if (url == null) return

    Glide.with(this)
        .load(url)
        .placeholder(
            placeholder ?: AppCompatResources.getDrawable(
                context,
                R.drawable.baseline_music_note_24
            )
        )
        .apply {
            isRounded?.let { if (isRounded) circleCrop() }
        }
        .into(this)
}

@BindingAdapter("drawableResource")
fun ImageView.setDrawableResource(@DrawableRes drawableResource: Int) {
    setImageResource(drawableResource)
}