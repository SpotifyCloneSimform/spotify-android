package com.simformsolutions.myspotify.ui.fragment

import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.ItemType
import com.simformsolutions.myspotify.data.model.local.LibraryItemType
import com.simformsolutions.myspotify.data.model.local.SearchItem
import com.simformsolutions.myspotify.databinding.FragmentSearchHistoryBinding
import com.simformsolutions.myspotify.extentions.getThemeColor
import com.simformsolutions.myspotify.listener.ItemClickListener
import com.simformsolutions.myspotify.ui.adapter.SearchAdapter
import com.simformsolutions.myspotify.ui.base.BaseFragment
import com.simformsolutions.myspotify.ui.dialog.TrackOptionsDialog
import com.simformsolutions.myspotify.ui.viewmodel.MainViewModel
import com.simformsolutions.myspotify.ui.viewmodel.SearchHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchHistoryFragment : BaseFragment<FragmentSearchHistoryBinding, SearchHistoryViewModel>(),
    SearchView.OnQueryTextListener {

    private lateinit var searchAdapter: SearchAdapter
    private lateinit var searchView: SearchView
    private val activityViewModel: MainViewModel by activityViewModels()

    override val viewModel: SearchHistoryViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_search_history

    override fun initialize() {
        super.initialize()
        setupUI()
    }

    override fun initializeObservers() {
        super.initializeObservers()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.searchItems.collectLatest { list ->
                    searchAdapter.submitList(list)
                }
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_toolbar_search_history, menu)
        searchView = menu.getItem(0).actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onDestroyView() {
        activityViewModel.setAppBarScrollingEnabled(true)
        activityViewModel.updateToolbarColor(null)
        super.onDestroyView()
    }

    private fun setupUI() {
        activityViewModel.setAppBarScrollingEnabled(false)
        activityViewModel.updateToolbarColor(requireActivity().getThemeColor(com.google.android.material.R.attr.colorSurface))
        searchAdapter = SearchAdapter { item ->
            showTrackOptions(item.id)
        }
        setupItemClickNavigation()
        binding.rvSearchHistory.adapter = searchAdapter
    }

    private fun showTrackOptions(trackId: String) {
        val dialog = TrackOptionsDialog().apply {
            this.trackId = trackId
        }
        dialog.show(childFragmentManager, TrackOptionsDialog.TAG)
    }

    private fun setupItemClickNavigation() {
        searchAdapter.itemClickListener = object : ItemClickListener<SearchItem> {
            override fun onClick(item: SearchItem, position: Int) {
                val destination = when (item.type) {
                    getString(R.string.playlist) -> SearchHistoryFragmentDirections.actionSearchHistoryFragmentToViewPlaylistFragment(
                        item.id,
                        LibraryItemType.PLAYLIST
                    )

                    getString(R.string.track) -> SearchHistoryFragmentDirections.actionSearchHistoryFragmentToNowPlayingFragment(
                        item.id,
                        item.id,
                        ItemType.TRACK
                    )

                    getString(R.string.album) -> SearchHistoryFragmentDirections.actionSearchHistoryFragmentToViewPlaylistFragment(
                        item.id,
                        LibraryItemType.ALBUM
                    )

                    getString(R.string.artist) -> SearchHistoryFragmentDirections.actionSearchHistoryFragmentToViewArtistProfile(
                        item.id
                    )

                    else -> null
                }
                destination?.let { findNavController().navigate(it) }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.search(newText)
        return true
    }
}