package com.simformsolutions.myspotify.ui.base

import com.google.gson.Gson
import com.simformsolutions.myspotify.data.repository.ApiError
import com.simformsolutions.myspotify.utils.Resource
import retrofit2.Response

abstract class BaseRepository {

    fun <T> handleResponse(response: Response<T>): Resource<T> {
        return try {
            if (response.isSuccessful) {
                return Resource.Success(response.body())
            } else {
                if (response.code() in 400..499) {
                    response.errorBody().let {
                        val errorResponse = Gson().fromJson(
                            response.errorBody()?.string(), ApiError::class.java
                        )
                        return Resource.Error(errorResponse.message)
                    }
                } else {
                    return Resource.Error(response.message())
                }
            }
        } catch (e: Error) {
            Resource.Error(e.message ?: "Something went wrong!")
        }
    }
}