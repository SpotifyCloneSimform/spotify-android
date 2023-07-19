package com.simformsolutions.myspotify.ui.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.databinding.FragmentCategoryPlaylistBinding
import com.simformsolutions.myspotify.ui.adapter.SearchAdapter
import com.simformsolutions.myspotify.ui.base.BaseFragment
import com.simformsolutions.myspotify.ui.viewmodel.CategoryPlaylistViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryPlaylistFragment :
    BaseFragment<FragmentCategoryPlaylistBinding, CategoryPlaylistViewModel>() {

    private lateinit var playlistAdapter: SearchAdapter
    private val args: CategoryPlaylistFragmentArgs by navArgs()

    override val viewModel: CategoryPlaylistViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_category_playlist

    override fun initialize() {
        super.initialize()
        setupUI()
    }

    override fun initializeObservers() {
        super.initializeObservers()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.playlists.collectLatest { list ->
                    playlistAdapter.submitList(list)
                }
            }
        }
    }

    private fun setupUI() {
        playlistAdapter = SearchAdapter()
        binding.rvCategoryPlaylist.adapter = playlistAdapter
        viewModel.getPlaylists(args.categoryId)
    }
}