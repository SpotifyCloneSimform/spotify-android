package com.simformsolutions.myspotify.data.repository

import com.simformsolutions.myspotify.data.model.AuthResponse
import com.simformsolutions.myspotify.data.model.remote.HomeAlbum
import com.simformsolutions.myspotify.data.model.remote.HomeFeaturedPlaylist
import com.simformsolutions.myspotify.data.model.remote.HomePlaylist
import com.simformsolutions.myspotify.data.service.HomeService
import com.simformsolutions.myspotify.ui.base.BaseRepository
import com.simformsolutions.myspotify.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HomeRepository(private val homeService: HomeService):  BaseRepository() {

    suspend fun getPlaylists() = flow<Resource<HomePlaylist>> {
        emit(Resource.Loading())
        homeService.getPlaylist().let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getSongAlbum() = flow<Resource<HomeAlbum>> {
        emit(Resource.Loading())
        homeService.getSongAlbums().let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getFeaturedPlaylist() = flow<Resource<HomeFeaturedPlaylist>> {
        emit(Resource.Loading())
        homeService.getFeaturedPlaylist().let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)
}