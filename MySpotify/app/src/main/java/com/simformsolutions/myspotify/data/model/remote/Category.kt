package com.simformsolutions.myspotify.data.model.remote

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("categories") val categories: Categories
)

data class Categories(
    @SerializedName("href") val href: String,
    @SerializedName("items") val items: List<Category>,
    @SerializedName("limit") val limit: Int,
    @SerializedName("next") val next: String,
    @SerializedName("offset") val offset: Int,
    @SerializedName("previous") val previous: String?,
    @SerializedName("total") val total: Int
)

data class Category(
    @SerializedName("href") val href: String,
    @SerializedName("icons") val images: List<Image>,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)