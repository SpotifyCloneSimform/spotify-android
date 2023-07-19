package com.simformsolutions.myspotify.data.model.local

data class TrackItem(
    val id: String,
    val image: String?,
    val title: String,
    val type: String,
    val artists: String,
    var description: String = ""
)