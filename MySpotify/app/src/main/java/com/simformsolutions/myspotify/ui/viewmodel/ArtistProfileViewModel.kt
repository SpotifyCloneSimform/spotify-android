package com.simformsolutions.myspotify.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.simformsolutions.myspotify.data.model.local.DisplaySongData
import com.simformsolutions.myspotify.data.model.local.LibraryItemType
import com.simformsolutions.myspotify.data.model.remote.Artist
import com.simformsolutions.myspotify.data.model.remote.RelatedArtist
import com.simformsolutions.myspotify.data.repository.ArtistProfileRepository
import com.simformsolutions.myspotify.ui.base.BaseViewModel
import com.simformsolutions.myspotify.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistProfileViewModel @Inject constructor(
    private val artistProfileRepository: ArtistProfileRepository
) : BaseViewModel() {

    private val _artistProfile = MutableStateFlow<Artist?>(null)
    var artistProfile = _artistProfile.asStateFlow()

    private val _topTracks = MutableStateFlow<List<DisplaySongData>>(emptyList())
    var topTracks = _topTracks.asStateFlow()

    private val _relatedArtist = MutableStateFlow<RelatedArtist?>(null)
    var relatedArtist = _relatedArtist.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    fun getArtistProfile(artistId: String) {
        viewModelScope.launch {
            launch {
                artistProfileRepository.getArtistProfile(artistId).collectLatest { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            resource.data?.let { artist ->
                                _artistProfile.emit(artist)
                            }
                        }

                        is Resource.Error -> {}
                        is Resource.Loading -> {
                            _isLoading.emit(true)
                        }
                    }
                }
            }

            launch {
                artistProfileRepository.getArtistTopTracks(artistId).collectLatest { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            resource.data.let { topTracks ->
                                val data = topTracks?.track?.map { track ->
                                    val artists = track.artists.map { it.name }.joinToString(", ")
                                    DisplaySongData(
                                        track.name,
                                        artists,
                                        track.album.images.firstOrNull()?.url,
                                        LibraryItemType.ARTISTS,
                                        track.id,
                                        track.durationMs
                                    )
                                }
                                data?.let { _topTracks.emit(data) }
                            }
                        }

                        is Resource.Error -> {}
                        is Resource.Loading -> {
                            _isLoading.emit(true)
                        }
                    }
                }
            }

            launch {
                artistProfileRepository.getRelatedArtist(artistId).collectLatest { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            resource.data?.let { artists ->
                                _relatedArtist.emit(artists)
                            }
                        }

                        is Resource.Error -> {}
                        is Resource.Loading -> {
                            _isLoading.emit(true)
                        }
                    }
                }
            }
        }
    }
}