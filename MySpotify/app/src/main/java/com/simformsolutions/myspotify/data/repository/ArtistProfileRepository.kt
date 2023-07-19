package com.simformsolutions.myspotify.data.repository

import com.simformsolutions.myspotify.data.model.remote.Artist
import com.simformsolutions.myspotify.data.model.remote.RelatedArtist
import com.simformsolutions.myspotify.data.model.remote.TopTracks
import com.simformsolutions.myspotify.data.service.ViewArtistProfileService
import com.simformsolutions.myspotify.ui.base.BaseRepository
import com.simformsolutions.myspotify.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ArtistProfileRepository(
    private val artistProfileService: ViewArtistProfileService
): BaseRepository() {

    suspend fun getArtistProfile(artistId: String) = flow<Resource<Artist>> {
        artistProfileService.getArtistProfile(artistId).let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getArtistTopTracks(artistId: String) = flow<Resource<TopTracks>> {
        artistProfileService.getArtistTopTracks(artistId).let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getRelatedArtist(artistId: String) = flow<Resource<RelatedArtist>> {
        artistProfileService.getRelatedArtists(artistId).let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)

}