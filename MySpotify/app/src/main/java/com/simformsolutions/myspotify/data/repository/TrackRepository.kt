package com.simformsolutions.myspotify.data.repository

import com.simformsolutions.myspotify.data.model.remote.Track
import com.simformsolutions.myspotify.data.service.TrackService
import com.simformsolutions.myspotify.ui.base.BaseRepository
import com.simformsolutions.myspotify.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TrackRepository(private val trackService: TrackService) :
    BaseRepository() {

    suspend fun getTrackDetail(trackId: String) = flow<Resource<Track>> {
        emit(Resource.Loading())
        trackService.getTrackDetail(trackId).let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun checkLike(trackId: List<String>) = flow<Resource<List<Boolean>>> {
        emit(Resource.Loading())
        trackService.checkLike(trackId).let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun likeTrack(trackId: List<String>) = flow<Resource<Unit>> {
        emit(Resource.Loading())
        trackService.likeTrack(trackId).let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun removeLike(trackId: List<String>) = flow<Resource<Unit>> {
        emit(Resource.Loading())
        trackService.removeLike(trackId).let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)
}