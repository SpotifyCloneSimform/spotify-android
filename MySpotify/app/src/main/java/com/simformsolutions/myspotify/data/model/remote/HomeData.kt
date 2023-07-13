package com.simformsolutions.myspotify.data.model.remote

data class HomeData(
    val sectionName: String,
    val homeDisplayData: List<HomeDisplayData>
)

data class HomeDisplayData (
    val image: String,
    val name: String
)