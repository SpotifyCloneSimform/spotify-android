package com.simformsolutions.myspotify.ui.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.DisplaySongData
import com.simformsolutions.myspotify.data.model.local.ItemType
import com.simformsolutions.myspotify.data.model.local.LibraryItemType
import com.simformsolutions.myspotify.databinding.FragmentViewPlaylistBinding
import com.simformsolutions.myspotify.listener.ItemClickListener
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

    private lateinit var viewPlaylistSongAdapter: ViewPlaylistSongsAdapter

    override fun initialize() {
        super.initialize()
        setupUI()
    }

    private fun setupUI() {
        binding.btnPlay.visibility = View.GONE
        viewPlaylistSongAdapter = ViewPlaylistSongsAdapter()
        viewPlaylistSongAdapter.itemClickListener = object : ItemClickListener<DisplaySongData> {
            override fun onClick(item: DisplaySongData, position: Int) {
                item.id?.let { id ->
                    playTrack(id)
                }
            }
        }
        requireActivity().title = args.type.getLocalizedName(requireContext())
        binding.rvSong.adapter = viewPlaylistSongAdapter
        when (args.type) {
            LibraryItemType.PLAYLIST -> {
                viewModel.getPlaylistSong(args.playlistId)
            }

            LibraryItemType.ALBUM -> {
                viewModel.getAlbumSongs(args.playlistId)
            }

            else -> {}
        }
        binding.btnPlay.setOnClickListener {
            viewModel.playlistsSongs.value?.data?.firstOrNull()?.id?.let { id ->
                playTrack(id)
            }
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
                            viewPlaylistSongAdapter.submitList(it)
                        }
                    }
                }

                launch {
                    viewModel.albumFooterView.collectLatest { footerView ->
                        binding.additionInfo = footerView
                    }
                }
            }
        }
    }

    private fun playTrack(trackId: String) {
        val destination =
            ViewPlaylistFragmentDirections.actionViewPlaylistFragmentToNowPlayingFragment(
                trackId, args.playlistId, ItemType.valueOf(args.type.name)
            )
        findNavController().navigate(destination)
    }
}