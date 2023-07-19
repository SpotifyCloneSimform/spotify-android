package com.simformsolutions.myspotify.ui.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.ItemType
import com.simformsolutions.myspotify.data.model.local.LibraryItemType
import com.simformsolutions.myspotify.databinding.FragmentViewPlaylistBinding
import com.simformsolutions.myspotify.ui.adapter.ViewPlaylistSongsAdapter
import com.simformsolutions.myspotify.ui.base.BaseFragment
import com.simformsolutions.myspotify.ui.viewmodel.ViewPlaylistViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ViewPlaylistFragment : BaseFragment<FragmentViewPlaylistBinding, ViewPlaylistViewModel>() {

    private val args: ViewPlaylistFragmentArgs by navArgs()

    override val viewModel: ViewPlaylistViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_view_playlist

    private val adapter = ViewPlaylistSongsAdapter()

    override fun initialize() {
        super.initialize()
        setupUI()
    }

    private fun setupUI() {
        binding.btnPlay.visibility = View.GONE
        binding.rvSong.adapter = adapter
        when (args.type) {
            LibraryItemType.PLAYLIST -> {
                viewModel.getPlaylistSong(args.playlistId)
            }

            LibraryItemType.ALBUM -> {
                viewModel.getAlbumSongs(args.playlistId)
            }

            else -> {}
        }
    }

    override fun initializeObservers() {
        super.initializeObservers()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.playlistsSongs.collectLatest { displaySongs ->
                        binding.playlistInfo = displaySongs
                        displaySongs?.data?.let {
                            binding.btnPlay.visibility = View.VISIBLE
                            adapter.submitList(it)
                        }
                    }
                }

                launch {
                    viewModel.albumFooterView.collectLatest { footerView ->
                        binding.additionInfo = footerView
                        footerView?.artistId?.let {
                            viewModel.getArtist(it)
                        }
                    }
                }
            }
        }
    }
}