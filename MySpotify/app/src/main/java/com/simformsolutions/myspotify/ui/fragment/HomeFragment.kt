package com.simformsolutions.myspotify.ui.fragment

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.databinding.FragmentHomeBinding
import com.simformsolutions.myspotify.ui.adapter.HomeAdapter
import com.simformsolutions.myspotify.ui.base.BaseFragment
import com.simformsolutions.myspotify.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_home

    private val adapter = HomeAdapter {
        val destination =
            HomeFragmentDirections.actionHomeFragmentToViewPlaylistFragment(it.id, it.type)
        findNavController().navigate(destination)
    }

    override fun initialize() {
        super.initialize()
        setupUI()
    }

    override fun initializeObservers() {
        super.initializeObservers()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.errorMessage.collectLatest {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
                launch {
                    viewModel.playlist.collectLatest { playlists ->
                        adapter.submitList(playlists)
                    }
                }
            }
        }
    }

    private fun setupUI() {
        binding.rvHome.adapter = adapter
        viewModel.getPlaylists()
        viewModel.getSongAlbum()
        viewModel.getFeaturedPlaylist()
    }
}