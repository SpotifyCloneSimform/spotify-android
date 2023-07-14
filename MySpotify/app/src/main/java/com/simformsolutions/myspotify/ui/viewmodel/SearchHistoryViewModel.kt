package com.simformsolutions.myspotify.ui.viewmodel

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.SearchItem
import com.simformsolutions.myspotify.data.model.remote.SearchResponse
import com.simformsolutions.myspotify.data.repository.SearchRepository
import com.simformsolutions.myspotify.ui.base.BaseViewModel
import com.simformsolutions.myspotify.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val resources: Resources
) : BaseViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _searchItems = MutableStateFlow<List<SearchItem>>(emptyList())
    val searchItems = _searchItems.asStateFlow()

    private var searchJob: Job? = null
    private var limit = 20
    private var offset = 0

    fun search(query: String?) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(DEBOUNCE_TIME)
            if (query.isNullOrEmpty()) {
                _searchItems.emit(emptyList())
                return@launch
            }
            fetchSearchItems(query)
        }
    }

    private suspend fun fetchSearchItems(query: String) {
        val params = mapOf<String, Any>(
            "q" to query,
            "type" to "album,track,playlist",
            "limit" to limit,
            "offset" to offset,
            "include_external" to true
        )

        searchRepository.getSearchItems(params).collectLatest { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _isLoading.emit(true)
                }

                is Resource.Success -> {
                    resource.data?.let { data ->
                        val items = parseData(data)
                        _searchItems.emit(items)
                    }
                    _isLoading.emit(false)
                }

                is Resource.Error -> {
                    _isLoading.emit(false)
                }
            }
        }
    }

    private fun parseData(data: SearchResponse) = buildList<SearchItem> {
        data.tracks?.items?.forEach { track ->
            val artists = track.artists.map { it.name }
            add(
                SearchItem(
                    track.album.images.firstOrNull()?.url,
                    track.name,
                    resources.getString(R.string.track),
                    artists.joinToString(", ")
                )
            )
        }

        data.playlists?.items?.forEach { playlist ->
            val artist = playlist.owner.displayName
            add(
                SearchItem(
                    playlist.images.firstOrNull()?.url,
                    playlist.name,
                    resources.getString(R.string.playlist),
                    artist
                )
            )
        }

        data.albums?.items?.forEach { album ->
            val artists = album.artists.map { it.name }
            add(
                SearchItem(
                    album.images.firstOrNull()?.url,
                    album.name,
                    resources.getString(R.string.album),
                    artists.joinToString(", ")
                )
            )
        }
    }

    companion object {
        const val DEBOUNCE_TIME = 500L // milliseconds
    }
}