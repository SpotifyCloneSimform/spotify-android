package com.simformsolutions.myspotify.data.repository

import com.simformsolutions.myspotify.data.model.AuthResponse
import com.simformsolutions.myspotify.data.service.AuthService
import com.simformsolutions.myspotify.ui.base.BaseRepository
import com.simformsolutions.myspotify.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepository(
    private val authService: AuthService
) : BaseRepository() {

    suspend fun getAuthorizationToken(body: HashMap<String, String>) = flow<Resource<AuthResponse>> {
        emit(Resource.Loading())
        authService.getAuthorizationToken(body).let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)
}