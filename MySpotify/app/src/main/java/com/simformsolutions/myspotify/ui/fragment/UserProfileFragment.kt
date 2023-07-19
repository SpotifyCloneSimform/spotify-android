package com.simformsolutions.myspotify.ui.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.LibraryItemType
import com.simformsolutions.myspotify.data.model.local.ProfileMediaItem
import com.simformsolutions.myspotify.databinding.FragmentUserProfileBinding
import com.simformsolutions.myspotify.listener.ItemClickListener
import com.simformsolutions.myspotify.ui.adapter.ProfileMediaAdapter
import com.simformsolutions.myspotify.ui.base.BaseFragment
import com.simformsolutions.myspotify.ui.viewmodel.UserProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserProfileFragment : BaseFragment<FragmentUserProfileBinding, UserProfileViewModel>() {

    private lateinit var profileMediaAdapter: ProfileMediaAdapter

    override val viewModel: UserProfileViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_user_profile

    override fun initialize() {
        super.initialize()
        setupUI()
    }

    override fun initializeObservers() {
        super.initializeObservers()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.mediaItems.collectLatest { list ->
                    profileMediaAdapter.submitList(list)
                }
            }
        }
    }

    private fun setupUI() {
        profileMediaAdapter = ProfileMediaAdapter()
        profileMediaAdapter.itemClickListener = object : ItemClickListener<ProfileMediaItem> {
            override fun onClick(item: ProfileMediaItem, position: Int) {
                val destination =
                    UserProfileFragmentDirections.actionUserProfileFragmentToViewPlaylistFragment(
                        item.playlistId, LibraryItemType.PLAYLIST
                    )
                findNavController().navigate(destination)
            }
        }
        binding.rvPlaylists.adapter = profileMediaAdapter
    }
}