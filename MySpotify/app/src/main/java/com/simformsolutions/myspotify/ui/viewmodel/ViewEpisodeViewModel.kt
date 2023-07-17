package com.simformsolutions.myspotify.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.simformsolutions.myspotify.data.model.remote.Episode
import com.simformsolutions.myspotify.data.model.remote.MediaItems
import com.simformsolutions.myspotify.data.repository.ViewEpisodeRepository
import com.simformsolutions.myspotify.ui.base.BaseViewModel
import com.simformsolutions.myspotify.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewEpisodeViewModel @Inject constructor(
    private val viewEpisodeRepository: ViewEpisodeRepository
): BaseViewModel() {

    private val _episodes = MutableStateFlow<MediaItems<Episode>?>(null)
    val episodes = _episodes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getEpisodes(id: String) {
        viewModelScope.launch {
            viewEpisodeRepository.getEpisodes(id).collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { episodes ->
                            _episodes.emit( episodes)
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