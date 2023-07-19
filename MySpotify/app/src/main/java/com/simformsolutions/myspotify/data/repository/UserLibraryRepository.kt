package com.simformsolutions.myspotify.data.repository

import com.simformsolutions.myspotify.data.model.remote.Album
import com.simformsolutions.myspotify.data.model.remote.Artist
import com.simformsolutions.myspotify.data.model.remote.FollowedArtists
import com.simformsolutions.myspotify.data.model.remote.MediaItems
import com.simformsolutions.myspotify.data.model.remote.Playlist
import com.simformsolutions.myspotify.data.model.remote.SavedAlbum
import com.simformsolutions.myspotify.data.model.remote.Show
import com.simformsolutions.myspotify.data.model.remote.Shows
import com.simformsolutions.myspotify.data.service.UserLibraryService
import com.simformsolutions.myspotify.ui.base.BaseRepository
import com.simformsolutions.myspotify.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserLibraryRepository (private val userLibraryService: UserLibraryService): BaseRepository() {

    suspend fun getPlaylists() = flow<Resource<MediaItems<Playlist>>> {
        emit(Resource.Loading())
        userLibraryService.getPlaylists().let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getAlbums() = flow<Resource<MediaItems<SavedAlbum>>> {
        emit(Resource.Loading())
        userLibraryService.getAlbums().let { response ->
            val resource = handleResponse((response))
            emit(resource)
        }
    }

    suspend fun getFollowedArtists() = flow<Resource<FollowedArtists>> {
        userLibraryService.getFollowedArtists().let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }

    suspend fun getSavedEpisodes() = flow<Resource<MediaItems<Shows>>> {
        emit(Resource.Loading())
        userLibraryService.getSavedShows().let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)
}