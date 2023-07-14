package com.simformsolutions.myspotify.data.repository

import com.simformsolutions.myspotify.data.model.remote.MediaItems
import com.simformsolutions.myspotify.data.model.remote.Playlist
import com.simformsolutions.myspotify.data.service.PlaylistService
import com.simformsolutions.myspotify.ui.base.BaseRepository
import com.simformsolutions.myspotify.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PlaylistRepository(
    private val playlistService: PlaylistService
) : BaseRepository() {

    suspend fun getAuthorizedUserPlaylists() = flow<Resource<MediaItems<Playlist>>> {
        emit(Resource.Loading())
        playlistService.getAuthorizedUserPlaylists().let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getPlaylist(playlistId: String, fields: String? = null) = flow<Resource<Playlist>> {
        emit(Resource.Loading())
        playlistService.getPlaylist(playlistId, fields).let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)
}