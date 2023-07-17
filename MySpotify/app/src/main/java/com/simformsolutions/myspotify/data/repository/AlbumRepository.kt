package com.simformsolutions.myspotify.data.repository

import com.simformsolutions.myspotify.data.model.remote.Album
import com.simformsolutions.myspotify.data.service.AlbumService
import com.simformsolutions.myspotify.ui.base.BaseRepository
import com.simformsolutions.myspotify.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AlbumRepository(
    private val albumService: AlbumService
) : BaseRepository() {

    suspend fun getAlbum(albumId: String) = flow<Resource<Album>> {
        emit(Resource.Loading())
        albumService.getAlbum(albumId).let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)
}