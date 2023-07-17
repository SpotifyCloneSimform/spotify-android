package com.simformsolutions.myspotify.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.simformsolutions.myspotify.data.model.local.ItemType
import com.simformsolutions.myspotify.data.model.local.HomeData
import com.simformsolutions.myspotify.data.model.local.HomeDisplayData
import com.simformsolutions.myspotify.data.model.local.LibraryItemType
import com.simformsolutions.myspotify.data.repository.HomeRepository
import com.simformsolutions.myspotify.ui.base.BaseViewModel
import com.simformsolutions.myspotify.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : BaseViewModel() {

    private val _playlists = MutableStateFlow<List<HomeData>>(emptyList())
    var playlist = _playlists.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    fun getPlaylists() {
        viewModelScope.launch {
            homeRepository.getPlaylists().collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }

                    is Resource.Success -> {
                        resource.data.let { data ->
                            val data = data?.items?.mapNotNull { item ->
                                item.images.firstOrNull()?.url?.let {
                                    HomeDisplayData(
                                        it, item.name ?: "",
                                        item.id,
                                        LibraryItemType.PLAYLIST
                                    )
                                }
                            }
                            data?.let { data ->
                                _playlists.emit(_playlists.value + HomeData("Your playlist", data))
                            }
                        }
                    }

                    is Resource.Error -> {
                        _isLoading.emit(false)
                        resource.message?.let { _errorMessage.emit(it) }
                    }

                }
            }
        }
    }

    fun getSongAlbum() {
        viewModelScope.launch {
            homeRepository.getSongAlbum().collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }

                    is Resource.Success -> {
                        resource.data.let { data ->
                            val data = data?.items?.mapNotNull { item ->
                                item.album.images?.first()?.url?.let {
                                    item.album.id?.let { it1 ->
                                        HomeDisplayData(
                                            it,
                                            item.album.name ?: "N/A",
                                            it1,
                                            LibraryItemType.ALBUM
                                        )
                                    }
                                }
                            }
                            data?.let { data ->
                                _playlists.emit(_playlists.value + HomeData("Your Songs", data))
                            }
                        }
                    }

                    is Resource.Error -> {
                        _isLoading.emit(false)
                        resource.message?.let { _errorMessage.emit(it) }
                    }
                }
            }
        }
    }

    fun getFeaturedPlaylist() {
        viewModelScope.launch {
            homeRepository.getFeaturedPlaylist().collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }

                    is Resource.Success -> {
                        resource.data.let { data ->
                            val data = data?.playlists?.items?.mapNotNull { item ->
                                item.images.firstOrNull()?.url?.let {
                                    item.id?.let { it1 ->
                                        HomeDisplayData(
                                            it,
                                            item.name ?: "N/A",
                                            it1,
                                            LibraryItemType.PLAYLIST
                                        )
                                    }
                                }
                            }
                            data?.let { data ->
                                val message = resource.data?.message
                                _playlists.emit(
                                    _playlists.value + HomeData(
                                        message ?: "Featured Playlist", data
                                    )
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        _isLoading.emit(false)
                        resource.message?.let { _errorMessage.emit(it) }
                    }
                }
            }
        }
    }

}