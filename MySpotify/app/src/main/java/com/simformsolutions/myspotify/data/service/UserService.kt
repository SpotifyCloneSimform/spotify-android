package com.simformsolutions.myspotify.data.service

import com.simformsolutions.myspotify.data.model.remote.UserProfile
import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET("me")
    suspend fun getAuthorizedProfile(): Response<UserProfile>
}