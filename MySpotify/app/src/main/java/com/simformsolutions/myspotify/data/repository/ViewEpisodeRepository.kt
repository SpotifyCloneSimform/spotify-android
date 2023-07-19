package com.simformsolutions.myspotify.data.repository

import com.simformsolutions.myspotify.data.model.remote.Episode
import com.simformsolutions.myspotify.data.model.remote.MediaItems
import com.simformsolutions.myspotify.data.service.ViewEpisodesService
import com.simformsolutions.myspotify.ui.base.BaseRepository
import com.simformsolutions.myspotify.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ViewEpisodeRepository(
    private val viewEpisodesService: ViewEpisodesService
): BaseRepository() {

    suspend fun getEpisodes(id: String) = flow<Resource<MediaItems<Episode>>> {
        viewEpisodesService.getEpisodes(id).let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)

}