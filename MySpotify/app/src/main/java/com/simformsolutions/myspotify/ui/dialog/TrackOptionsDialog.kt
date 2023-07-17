package com.simformsolutions.myspotify.ui.dialog

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.TrackOptionItem
import com.simformsolutions.myspotify.databinding.DialogFragmentTrackOptionsBinding
import com.simformsolutions.myspotify.listener.ItemClickListener
import com.simformsolutions.myspotify.ui.adapter.TrackOptionAdapter
import com.simformsolutions.myspotify.ui.base.BaseBottomDialog
import com.simformsolutions.myspotify.ui.viewmodel.TrackOptionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrackOptionsDialog :
    BaseBottomDialog<DialogFragmentTrackOptionsBinding, TrackOptionsViewModel>() {

    private lateinit var trackOptionAdapter: TrackOptionAdapter
    var trackId: String? = null

    override val viewModel: TrackOptionsViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.dialog_fragment_track_options

    override fun initialize() {
        super.initialize()
        setupUI()
    }

    override fun initializeObservers() {
        super.initializeObservers()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.trackOptions.collectLatest { list ->
                    trackOptionAdapter.submitList(list)
                }
            }
        }
    }

    private fun setupUI() {
        trackOptionAdapter = TrackOptionAdapter()
        trackOptionAdapter.itemClickListener = object : ItemClickListener<TrackOptionItem> {
            override fun onClick(item: TrackOptionItem, position: Int) {
                item.onClick?.invoke()
            }
        }
        binding.rvOptions.adapter = trackOptionAdapter
        trackId?.let { viewModel.getTrackDetail(it) }
    }

    companion object {
        const val TAG = "TrackOptionsDialog"
    }
}