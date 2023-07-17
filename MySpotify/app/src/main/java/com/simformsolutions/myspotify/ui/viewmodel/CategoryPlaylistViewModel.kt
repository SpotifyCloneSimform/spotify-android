package com.simformsolutions.myspotify.ui.viewmodel

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.SearchItem
import com.simformsolutions.myspotify.data.repository.PlaylistRepository
import com.simformsolutions.myspotify.ui.base.BaseViewModel
import com.simformsolutions.myspotify.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryPlaylistViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository,
    private val resources: Resources
) : BaseViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _playlists = MutableStateFlow<List<SearchItem>>(emptyList())
    val playlists = _playlists.asStateFlow()

    fun getPlaylists(categoryId: String) {
        viewModelScope.launch {
            playlistRepository.getCategoryPlaylists(categoryId).collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }

                    is Resource.Success -> {
                        resource.data?.let { data ->
                            val items = data.playlists.items.mapNotNull {
                                it?.let { playlist ->
                                    SearchItem(
                                        playlist.id,
                                        playlist.images.firstOrNull()?.url,
                                        playlist.name,
                                        resources.getString(R.string.playlist),
                                        playlist.owner.displayName ?: "Unknown"
                                    ) }
                            }
                            _playlists.emit(items)
                        }
                        _isLoading.emit(false)
                    }

                    is Resource.Error -> {
                        _isLoading.emit(false)
                    }
                }
            }
        }
    }
}