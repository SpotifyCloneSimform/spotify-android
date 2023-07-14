package com.simformsolutions.myspotify.data.repository

import com.simformsolutions.myspotify.data.model.remote.CategoryResponse
import com.simformsolutions.myspotify.data.model.remote.MediaItems
import com.simformsolutions.myspotify.data.model.remote.SearchResponse
import com.simformsolutions.myspotify.data.service.SearchService
import com.simformsolutions.myspotify.ui.base.BaseRepository
import com.simformsolutions.myspotify.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchRepository(
    private val searchService: SearchService
) : BaseRepository() {

    suspend fun getCategories() = flow<Resource<CategoryResponse>> {
        emit(Resource.Loading())
        searchService.getCategories().let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getSearchItems(params: Map<String, Any>) = flow<Resource<SearchResponse>> {
        emit(Resource.Loading())
        searchService.search(params).let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)
}