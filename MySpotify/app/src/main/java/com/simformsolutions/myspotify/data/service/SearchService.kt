package com.simformsolutions.myspotify.data.service

import com.simformsolutions.myspotify.data.model.remote.CategoryResponse
import com.simformsolutions.myspotify.data.model.remote.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SearchService {

    @GET("browse/categories")
    suspend fun getCategories(): Response<CategoryResponse>

    @JvmSuppressWildcards
    @GET("search")
    suspend fun search(@QueryMap params: Map<String, Any>): Response<SearchResponse>
}