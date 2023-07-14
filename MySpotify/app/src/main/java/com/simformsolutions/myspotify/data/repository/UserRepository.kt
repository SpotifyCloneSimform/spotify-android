package com.simformsolutions.myspotify.data.repository

import com.simformsolutions.myspotify.data.model.remote.UserProfile
import com.simformsolutions.myspotify.data.service.UserService
import com.simformsolutions.myspotify.ui.base.BaseRepository
import com.simformsolutions.myspotify.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(
    private val userService: UserService
) : BaseRepository() {

    suspend fun getAuthorizedUser() = flow<Resource<UserProfile>> {
        emit(Resource.Loading())
        userService.getAuthorizedProfile().let { response ->
            val resource = handleResponse(response)
            emit(resource)
        }
    }.flowOn(Dispatchers.IO)
}