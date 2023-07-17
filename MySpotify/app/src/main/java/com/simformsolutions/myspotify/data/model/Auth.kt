package com.simformsolutions.myspotify.data.model

import com.google.gson.annotations.SerializedName

enum class GrantType(private val value: String) {
    AUTHORIZATION_CODE("authorization_code"),
    REFRESH_TOKEN("refresh_token");

    override fun toString(): String {
        return value
    }
}

data class AuthResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String?,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expire_time")
    val expireTime: Int,
    @SerializedName("scope")
    val scope: String
)