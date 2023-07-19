package com.simformsolutions.myspotify.data.repository

import com.simformsolutions.myspotify.data.model.remote.PlaylistItems
import com.simformsolutions.myspotify.data.model.remote.HomeAlbumItems
import com.simformsolutions.myspotify.data.model.remote.MediaItems
import com.simformsolutions.myspotify.data.model.remote.Playlist
import com.simformsolutions.myspotify.data.service.HomeService
import com.simformsolutions.myspotify.ui.base.BaseRepository
import com.simformsolutions.myspotify.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HomeRepository(private val homeService: HomeService) : BaseRepository() {

    suspend fun getPlaylists() = flow<Resource<MediaItems<Playlist>>> {
        emit(Resource.Loading())
        homeService.getPlaylist().let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getSongAlbum() = flow<Resource<MediaItems<HomeAlbumItems>>> {
        emit(Resource.Loading())
        homeService.getSongAlbums().let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getFeaturedPlaylist() = flow<Resource<PlaylistItems>> {
        emit(Resource.Loading())
        homeService.getFeaturedPlaylist().let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)
}