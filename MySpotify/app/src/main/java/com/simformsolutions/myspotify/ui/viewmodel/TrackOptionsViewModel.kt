package com.simformsolutions.myspotify.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.TrackItem
import com.simformsolutions.myspotify.data.model.local.TrackOptionItem
import com.simformsolutions.myspotify.data.repository.TrackRepository
import com.simformsolutions.myspotify.ui.base.BaseViewModel
import com.simformsolutions.myspotify.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackOptionsViewModel @Inject constructor(
    private val trackRepository: TrackRepository
) : BaseViewModel() {

    private val _trackOptions = MutableStateFlow<List<TrackOptionItem>>(emptyList())
    val trackOptions = _trackOptions.asStateFlow()

    private val _song = MutableStateFlow<TrackItem?>(null)
    val song = _song.asStateFlow()

    private var trackId: String = ""
    private var isLiked: Boolean = false
    private val likeOption = TrackOptionItem(R.drawable.ic_heart_24, "Like") {
        toggleLike()
    }

    init {
        initializeTrackOptions()
    }

    fun getTrackDetail(trackId: String) {
        this.trackId = trackId
        updateDetails()
        viewModelScope.launch {
            trackRepository.getTrackDetail(trackId).collectLatest { resource ->
                if (resource is Resource.Success) {
                    resource.data?.let { track ->
                        val artists = track.artists.joinToString(", ") { it.name }
                        _song.emit(
                            TrackItem(
                                trackId, track.album.images.firstOrNull()?.url,
                                track.name,
                                "Track",
                                artists,
                                artists
                            )
                        )
                    }
                }
            }
        }
    }

    private fun initializeTrackOptions() {
        viewModelScope.launch {
            val options = listOf(
                likeOption,
                TrackOptionItem(R.drawable.ic_playlist_24, "Add to playlist"),
                TrackOptionItem(R.drawable.ic_album_24, "View album"),
                TrackOptionItem(R.drawable.ic_artist_24, "View artists"),
                TrackOptionItem(R.drawable.ic_share_24, "Share"),
            )
            _trackOptions.emit(options)
        }
    }

    private fun updateDetails() {
        checkLike()
    }

    private fun checkLike() {
        viewModelScope.launch {
            trackRepository.checkLike(listOf(trackId)).collectLatest { resource ->
                if (resource is Resource.Success) {
                    updateLikeStatus(resource.data?.firstOrNull() == true)
                }
            }
        }
    }

    private fun toggleLike() {
        if (isLiked) {
            removeLike()
        } else {
            performLike()
        }
    }

    private fun performLike() {
        viewModelScope.launch {
            trackRepository.likeTrack(listOf(trackId)).collectLatest { resource ->
                if (resource is Resource.Success) {
                    updateLikeStatus(true)
                }
            }
        }
    }

    private fun removeLike() {
        viewModelScope.launch {
            trackRepository.removeLike(listOf(trackId)).collectLatest { resource ->
                if (resource is Resource.Success) {
                    updateLikeStatus(false)
                }
            }
        }
    }

    private fun updateLikeStatus(isLiked: Boolean) {
        this.isLiked = isLiked
        if (isLiked) {
            likeOption.icon = R.drawable.ic_heart_active_24
            likeOption.title = "Liked"
        } else {
            likeOption.icon = R.drawable.ic_heart_24
            likeOption.title = "Like"
        }
    }
}