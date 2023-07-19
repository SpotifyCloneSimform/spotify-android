package com.simformsolutions.myspotify.ui.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.LibraryDisplay
import com.simformsolutions.myspotify.data.model.local.LibraryItemType
import com.simformsolutions.myspotify.data.model.local.ShowData
import com.simformsolutions.myspotify.databinding.FragmentUserLibraryBinding
import com.simformsolutions.myspotify.listener.ItemClickListener
import com.simformsolutions.myspotify.ui.adapter.UserLibraryAdapter
import com.simformsolutions.myspotify.ui.base.BaseFragment
import com.simformsolutions.myspotify.ui.viewmodel.UserLibraryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserLibraryFragment : BaseFragment<FragmentUserLibraryBinding, UserLibraryViewModel>() {

    override val viewModel: UserLibraryViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_user_library

    private val adapter = UserLibraryAdapter()

    override fun initialize() {
        super.initialize()
        setupUI()
    }

    override fun initializeObservers() {
        super.initializeObservers()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.libraryItems.collectLatest {
                        if (it.isNotEmpty()){
                            adapter.addItems(it)
                        }
                    }
                }

                launch {
                    viewModel.temp.collectLatest {
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun setupUI() {
        adapter.itemClickListener = object : ItemClickListener<LibraryDisplay> {
            override fun onClick(item: LibraryDisplay, position: Int) {
                if (item.type == LibraryItemType.PLAYLIST || item.type == LibraryItemType.ALBUM) {
                    val destination =
                        item.id?.let {
                            UserLibraryFragmentDirections.actionUserLibraryFragmentToViewPlaylistFragment(
                                it, item.type)
                        }
                    if (destination != null) {
                        findNavController().navigate(destination)
                    }
                } else if (item.type == LibraryItemType.ARTISTS) {
                    val destination =
                        item.id?.let {
                            UserLibraryFragmentDirections.actionUserLibraryFragmentToViewArtistProfile(
                                it
                            )
                        }
                    if (destination != null) {
                        findNavController().navigate(destination)
                    }
                } else if (item.type == LibraryItemType.PODCAST) {
                    val data = ShowData(item.id, item.description, item.image, item.name, item.ownerDisplayName)
                    val destination = UserLibraryFragmentDirections.actionUserLibraryFragmentToViewPodcastEpisodes(data)
                    findNavController().navigate(destination)
                }
            }
        }
        binding.chipAll.isChecked = true
        viewModel.getInitialLibraryItems()
        binding.rvLibraryItems.adapter = adapter
        val initialSelectedChipId = binding.category.checkedChipId
        setupChips(initialSelectedChipId)
        binding.category.setOnCheckedStateChangeListener { group, checkedIds ->
            setupChips(checkedIds.first())
        }
    }

    private fun setupChips(chipId: Int) {
        when (chipId) {
            binding.chipAll.id -> {
                viewModel.getInitialLibraryItems()
            }
            binding.chipPlaylist.id -> {
                viewModel.getPlaylists()
            }
            binding.chipAlbum.id -> {
                viewModel.getAlbum()
            }
            binding.chipArtists.id -> {
                viewModel.getArtists()
            }
            binding.chipPodcast.id -> {
                viewModel.getSavedShows()
            }
        }
    }

}