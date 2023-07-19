package com.simformsolutions.myspotify.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.simformsolutions.myspotify.data.model.local.ItemType
import com.simformsolutions.myspotify.data.model.local.TrackItem
import com.simformsolutions.myspotify.data.repository.AlbumRepository
import com.simformsolutions.myspotify.data.repository.ArtistProfileRepository
import com.simformsolutions.myspotify.data.repository.PlaylistRepository
import com.simformsolutions.myspotify.data.repository.TrackRepository
import com.simformsolutions.myspotify.ui.base.BaseViewModel
import com.simformsolutions.myspotify.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val trackRepository: TrackRepository,
    private val playlistRepository: PlaylistRepository,
    private val albumRepository: AlbumRepository,
    private val artistProfileRepository: ArtistProfileRepository
) : BaseViewModel() {

    private val _track = MutableStateFlow<TrackItem?>(null)
    val track = _track.asStateFlow()

    private val _name = MutableStateFlow<String>("")
    val name = _name.asStateFlow()

    val isShuffle = MutableStateFlow(false)
    private val trackQueue = mutableListOf<TrackItem>()
    private var currentTrackPosition = 0

    fun setupPlayingQueue(trackId: String, id: String, type: ItemType) {
        when (type) {
            ItemType.PLAYLIST -> getByPlaylist(trackId, id)
            ItemType.ALBUM -> getByAlbum(trackId, id)
            ItemType.TRACK -> getByTrack(id)
            ItemType.ARTIST -> getArtistTopTracks(trackId, id)
            else -> {}
        }
    }

    private fun getByTrack(trackId: String) {
        viewModelScope.launch {
            trackRepository.getTrackDetail(trackId).collectLatest { resource ->
                if (resource is Resource.Success) {
                    resource.data?.album?.id?.let { albumId -> getByAlbum(trackId, albumId) }
                }
            }
        }
    }

    private fun getByPlaylist(trackId: String, playlistId: String) {
        viewModelScope.launch {
            playlistRepository.getPlaylist(playlistId).collectLatest { resource ->
                if (resource is Resource.Success) {
                    val items = resource.data?.tracks?.items?.map { playlist ->
                        _name.emit(resource.data.name)
                        playlist.track.let { track ->
                            val artists = track.artists.joinToString(", ") { it.name }
                            TrackItem(
                                track.id,
                                track.album.images.firstOrNull()?.url,
                                track.name,
                                "Track",
                                artists
                            ).also { updateLike(it) }
                        }
                    }
                    items?.let {
                        trackQueue.clear()
                        trackQueue.addAll(it)
                        currentTrackPosition = it.indexOfFirst { item -> item.id == trackId }
                        updateCurrentTrack()
                    }
                }
            }
        }
    }

    private fun getByAlbum(trackId: String, albumId: String) {
        viewModelScope.launch {
            albumRepository.getAlbum(albumId).collectLatest { resource ->
                if (resource is Resource.Success) {
                    resource.data?.name?.let { _name.emit(it) }
                    val items = resource.data?.tracks?.items?.map { track ->
                        val artists = track.artists.joinToString(", ") { it.name }
                        TrackItem(
                            track.id,
                            resource.data.images.firstOrNull()?.url,
                            track.name,
                            "Track",
                            artists
                        ).also { updateLike(it) }
                    }
                    items?.let {
                        trackQueue.clear()
                        trackQueue.addAll(it)
                        currentTrackPosition = it.indexOfFirst { item -> item.id == trackId }
                        updateCurrentTrack()
                    }
                }
            }
        }
    }

    private fun getArtistTopTracks(trackId: String, artistId: String) {
        getArtistInfo(artistId)
        viewModelScope.launch {
            artistProfileRepository.getArtistTopTracks(artistId).collectLatest { resource ->
                if (resource is Resource.Success) {
                    val items = resource.data?.track?.map { track ->
                        val artists = track.artists.joinToString(", ") { it.name }
                        TrackItem(
                            track.id,
                            track.album.images.firstOrNull()?.url,
                            track.name,
                            "Track",
                            artists
                        ).also { updateLike(it) }
                    }
                    items?.let {
                        trackQueue.clear()
                        trackQueue.addAll(it)
                        currentTrackPosition = it.indexOfFirst { item -> item.id == trackId }
                        updateCurrentTrack()
                    }
                }
            }
        }
    }

    private fun getArtistInfo(artistId: String) {
        viewModelScope.launch {
            artistProfileRepository.getArtistProfile(artistId).collectLatest { resource ->
                if (resource is Resource.Success) {
                    resource.data?.name?.let { name ->
                        _name.emit(name)
                    }
                }
            }
        }
    }

    private fun updateLike(track: TrackItem) {
        viewModelScope.launch {
            trackRepository.checkLike(listOf(track.id)).collectLatest { resource ->
                if (resource is Resource.Success) {
                    track.isLiked = resource.data?.firstOrNull() == true
                }
            }
        }
    }

    private fun updateCurrentTrack() {
        viewModelScope.launch {
            trackQueue[currentTrackPosition].let { track ->
                _track.emit(track)
                updateLike(track)
            }
        }
    }

    fun nextTrack() {
        if (isShuffle.value) {
            currentTrackPosition = Random.nextInt(0, trackQueue.count())
        } else if (currentTrackPosition < trackQueue.count() - 1) {
            currentTrackPosition++
        } else {
            currentTrackPosition = 0
        }
        updateCurrentTrack()
    }

    fun previousTrack() {
        if (isShuffle.value) {
            currentTrackPosition = Random.nextInt(0, trackQueue.count())
        } else if (currentTrackPosition > 0) {
            currentTrackPosition--
        } else {
            currentTrackPosition = trackQueue.count() - 1
        }
        updateCurrentTrack()
    }
}
