package com.simformsolutions.myspotify.ui.adapter

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.remote.Episode
import com.simformsolutions.myspotify.databinding.ItemShowEpisodesBinding
import com.simformsolutions.myspotify.ui.base.BaseAdapter
import com.simformsolutions.myspotify.ui.binding.bindImage
import java.util.concurrent.TimeUnit

class EpisodesAdapter(var publisherName: String = "") : BaseAdapter<Episode>() {

    override fun getLayoutId(viewType: Int) = R.layout.item_show_episodes


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun setDataForListItemWithPosition(
        binding: ViewDataBinding,
        data: Episode,
        adapterPosition: Int
    ) {
        (binding as? ItemShowEpisodesBinding)?.let {

            val HH: Long = TimeUnit.MILLISECONDS.toHours(data.durationMs.toLong())
            val MM: Long = TimeUnit.MILLISECONDS.toMinutes(data.durationMs.toLong()) % 60
            val SS: Long = TimeUnit.MILLISECONDS.toSeconds(data.durationMs.toLong()) % 60

            binding.episode = data
            binding.tvDurtion.text = String.format("%02d:%02d:%02d", HH, MM, SS)
            binding.imgThumbnail.bindImage(
                data.images.firstOrNull()?.url,
                binding.root.context.getDrawable(R.drawable.baseline_music_note_24)
            )
            binding.tvShowName.text = publisherName
        }
    }
}