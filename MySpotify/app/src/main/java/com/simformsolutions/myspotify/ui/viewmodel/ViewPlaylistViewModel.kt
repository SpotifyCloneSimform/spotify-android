package com.simformsolutions.myspotify.ui.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.simformsolutions.myspotify.data.model.local.DisplayAlbumFooterView
import com.simformsolutions.myspotify.data.model.local.DisplaySong
import com.simformsolutions.myspotify.data.model.local.DisplaySongData
import com.simformsolutions.myspotify.data.model.local.ItemType
import com.simformsolutions.myspotify.data.model.remote.AlbumArtist
import com.simformsolutions.myspotify.data.repository.ViewPlaylistRepository
import com.simformsolutions.myspotify.ui.base.BaseViewModel
import com.simformsolutions.myspotify.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewPlaylistViewModel @Inject constructor(
    private val viewPlaylistRepository: ViewPlaylistRepository
) : BaseViewModel() {

    private val _playlistsSongs = MutableStateFlow<DisplaySong?>(null)
    var playlistsSongs = _playlistsSongs.asStateFlow()

    private val _albumFooterView = MutableStateFlow<DisplayAlbumFooterView?>(null)
    var albumFooterView = _albumFooterView.asStateFlow()

    private val _ablumArtist = MutableStateFlow<AlbumArtist?>(null)
    var ablumArtist = _ablumArtist.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    fun getPlaylistSong(playlistId: String) {
        viewModelScope.launch {
            viewPlaylistRepository.getPlaylistSong(playlistId).collectLatest { resource ->

                when (resource) {
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }

                    is Resource.Success -> {
                        resource.data?.let { songs ->
                            val songData = songs.tracks.items.map { item ->
                                val artists =
                                    item.track?.artists?.joinToString(", ") { it.name }
                                DisplaySongData(
                                    item.track?.name,
                                    artists,
                                    item.track?.album?.images?.firstOrNull()?.url,
                                    ItemType.PLAYLIST,
                                    item.track?.id,
                                    item.track?.durationMs
                                )
                            }
                            songData?.let { songData ->
                                _playlistsSongs.value = null
                                _playlistsSongs.emit(
                                    DisplaySong(
                                        ItemType.PLAYLIST,
                                        songs.images.firstOrNull()?.url,
                                        songs.name,
                                        songs.owner.displayName,
                                        songData
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

    fun getAlbumSongs(id: String) {
        viewModelScope.launch {
            viewPlaylistRepository.getAlbumSong(id).collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }

                    is Resource.Success -> {
                        resource.data?.let { albumSong ->
                            val songData = albumSong.tracks.items.map { item ->
                                val artist = item.artists.joinToString(", ") { it.name }
                                DisplaySongData(item.name, artist, "", ItemType.ALBUM, item.id, item.durationMs)
                            }

                            _playlistsSongs.value = null
                            _playlistsSongs.emit(DisplaySong(ItemType.ALBUM, albumSong.images.firstOrNull()?.url, albumSong.name, albumSong.artists.firstOrNull()?.name, songData))
                            _albumFooterView.emit(albumSong.artists.firstOrNull()?.id?.let {
                                DisplayAlbumFooterView(
                                    it, albumSong.releaseDate, albumSong.totalTracks, albumSong.copyrights.firstOrNull()?.text ?: "")
                            })

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

    fun getArtist(id: String) {
        viewModelScope.launch {
            viewPlaylistRepository.getAlbumArtist(id).collectLatest {
                    resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }

                    is Resource.Success -> {
                        resource.data?.let {
                            Log.d("artist", it.toString())
                            _ablumArtist.emit(it)
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