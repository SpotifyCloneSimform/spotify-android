package com.simformsolutions.myspotify.data.model.remote

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("status")
    val status: Int?,
    @SerializedName("message")
    val message: String?
)