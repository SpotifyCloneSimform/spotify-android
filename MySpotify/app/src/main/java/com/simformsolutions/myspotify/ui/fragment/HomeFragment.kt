package com.simformsolutions.myspotify.ui.fragment

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.databinding.FragmentHomeBinding
import com.simformsolutions.myspotify.ui.adapters.HomeRVAdapter
import com.simformsolutions.myspotify.ui.base.BaseFragment
import com.simformsolutions.myspotify.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_home

    private val adapter = HomeRVAdapter()

    override fun initialize() {
        super.initialize()
        binding.rvHome.adapter = adapter
        viewModel.getPlaylists()
        viewModel.getSongAlbum()
        viewModel.getFeaturedPlaylist()
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
                        Log.d("Data", playlists.toString())
                        adapter.submitList(playlists)
                    }
                }
            }
        }
    }

    private fun setupUI() {

    }
}