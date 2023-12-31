package com.simformsolutions.myspotify.data.model.local

data class SearchItem(
    val id: String,
    val image: String?,
    val title: String,
    val type: String,
    val artists: String,
    var hasMenu: Boolean = false
)