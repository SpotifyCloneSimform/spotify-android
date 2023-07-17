package com.simformsolutions.myspotify.data.bindingAdapter

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.simformsolutions.myspotify.R

@SuppressLint("CheckResult")
@BindingAdapter("imageUrl", "placeHolder", "isRounded", requireAll = false)
fun ImageView.bindImage(url: String?, @DrawableRes placeholder: Int?, isRounded: Boolean?) {
    if (url == null) return

    Glide.with(this)
        .load(url)
        .placeholder(placeholder ?: R.drawable.baseline_music_note_24)
        .apply {
            isRounded?.let { circleCrop() }
        }
        .into(this)
}