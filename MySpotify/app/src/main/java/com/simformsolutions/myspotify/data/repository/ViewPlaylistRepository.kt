package com.simformsolutions.myspotify.data.repository

import com.simformsolutions.myspotify.data.model.remote.Album
import com.simformsolutions.myspotify.data.model.remote.AlbumArtist
import com.simformsolutions.myspotify.data.model.remote.Playlist
import com.simformsolutions.myspotify.data.model.remote.ViewAlbum
import com.simformsolutions.myspotify.data.service.ViewPlaylistService
import com.simformsolutions.myspotify.ui.base.BaseRepository
import com.simformsolutions.myspotify.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ViewPlaylistRepository(private val viewPlaylistService: ViewPlaylistService) :
    BaseRepository() {

    suspend fun getPlaylistSong(playlistId: String) = flow<Resource<Playlist>> {
        emit(Resource.Loading())
        viewPlaylistService.getPlaylistItems(playlistId).let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getAlbumSong(albumId: String) = flow<Resource<ViewAlbum>> {
        emit(Resource.Loading())
        viewPlaylistService.getAlbumSongs(albumId).let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getAlbumArtist(artistId: String) = flow<Resource<AlbumArtist>> {
        viewPlaylistService.getArtist(artistId).let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }
}