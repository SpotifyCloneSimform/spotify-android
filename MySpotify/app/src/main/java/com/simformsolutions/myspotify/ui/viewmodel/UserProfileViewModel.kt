package com.simformsolutions.myspotify.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.simformsolutions.myspotify.data.model.local.ProfileMediaItem
import com.simformsolutions.myspotify.data.model.remote.UserProfile
import com.simformsolutions.myspotify.data.repository.PlaylistRepository
import com.simformsolutions.myspotify.data.repository.UserRepository
import com.simformsolutions.myspotify.helper.PreferenceHelper
import com.simformsolutions.myspotify.ui.base.BaseViewModel
import com.simformsolutions.myspotify.utils.PreferenceKeys
import com.simformsolutions.myspotify.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val playlistRepository: PlaylistRepository,
    preferenceHelper: PreferenceHelper
) : BaseViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile = _profile.asStateFlow()

    private val _mediaItems = MutableStateFlow<List<ProfileMediaItem>>(emptyList())
    val mediaItems = _mediaItems.asStateFlow()

    private val userId = preferenceHelper.getString(PreferenceKeys.USER_ID, "")

    init {
        getProfileDetails()
        getMediaItems()
    }

    private fun getProfileDetails() {
        viewModelScope.launch {
            userRepository.getAuthorizedUser().collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }

                    is Resource.Success -> {
                        _profile.emit(resource.data)
                        _isLoading.emit(false)
                    }

                    is Resource.Error -> {
                        _isLoading.emit(false)
                    }
                }
            }
        }
    }

    private fun getMediaItems() {
        getUserPlaylists()
    }

    private fun getUserPlaylists() {
        viewModelScope.launch {
            playlistRepository.getAuthorizedUserPlaylists().collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }

                    is Resource.Success -> {
                        val userItems = buildList {
                            resource.data?.items?.filter { it.owner.id == userId }
                                ?.forEach { playlist ->
                                    val item = ProfileMediaItem(
                                        playlist.id,
                                        playlist.images.firstOrNull()?.url,
                                        playlist.name
                                    )
                                    updatePlaylistDetails(playlist.id, item)
                                    add(item)
                                }
                        }
                        _mediaItems.emit(userItems)
                        _isLoading.emit(false)
                    }

                    is Resource.Error -> {
                        _isLoading.emit(false)
                    }
                }
            }
        }
    }

    private fun updatePlaylistDetails(playlistId: String, mediaItem: ProfileMediaItem) {
        val fields = "followers,tracks.items(track.artists.name)"
        viewModelScope.launch {
            playlistRepository.getPlaylist(playlistId, fields).collectLatest { resource ->
                resource.data?.let { playlist ->
                    mediaItem.followers = playlist.followers.total
                    mediaItem.artists =
                        playlist.tracks.items.firstOrNull()?.track?.artists?.joinToString(", ") { artist -> artist.name }
                }
            }
        }
    }
}