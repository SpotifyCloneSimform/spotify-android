package com.simformsolutions.myspotify.data.service

import com.simformsolutions.myspotify.data.model.AuthResponse
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("api/token")
    suspend fun getAuthorizationToken(@FieldMap body: HashMap<String, String>): Response<AuthResponse>
}