package com.simformsolutions.myspotify.ui.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.databinding.FragmentViewPodcastEpisodesBinding
import com.simformsolutions.myspotify.ui.adapter.EpisodesAdapter
import com.simformsolutions.myspotify.ui.base.BaseFragment
import com.simformsolutions.myspotify.ui.viewmodel.ViewEpisodeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ViewPodcastEpisodes : BaseFragment<FragmentViewPodcastEpisodesBinding, ViewEpisodeViewModel>() {

    override fun getLayoutResId() = R.layout.fragment_view_podcast_episodes

    override val viewModel: ViewEpisodeViewModel by viewModels()

    private val args: ViewPodcastEpisodesArgs by navArgs()

    private val adapter = EpisodesAdapter()

    override fun initialize() {
        super.initialize()
        setupUI()
    }

    override fun initializeObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.episodes.collectLatest { episodes ->
                    if (episodes != null) {
                        adapter.submitList(episodes.items)
                    }
                }
            }
        }
    }

    private fun setupUI() {
        binding.showData = args.showData
        binding.rvEpisodes.adapter = adapter
        adapter.publisherName = args.showData.publisher ?: "N/A"
        args.showData.id?.let { viewModel.getEpisodes(it) }
        binding.btnExpand.setOnClickListener {
            args.showData.isExpand = !args.showData.isExpand
        }
    }

}