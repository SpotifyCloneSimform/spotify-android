package com.simformsolutions.myspotify.ui.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
import java.util.Calendar

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
                        if (it.isNotEmpty()) {
                            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        }
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

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_toolbar_home, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.actionProfile) {
            val destination = HomeFragmentDirections.actionHomeFragmentToUserProfileFragment()
            findNavController().navigate(destination)
            return true
        }
        return false
    }

    private fun setupUI() {
        setupGreetingTitle()
        binding.rvHome.adapter = adapter
    }

    private fun setupGreetingTitle() {
        val title = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> getString(R.string.good_morning)
            in 12..15 -> getString(R.string.good_afternoon)
            in 16..20 -> getString(R.string.good_evening)
            in 21..23 -> getString(R.string.good_night)
            else -> getString(R.string.home)
        }
        requireActivity().title = title
    }
}