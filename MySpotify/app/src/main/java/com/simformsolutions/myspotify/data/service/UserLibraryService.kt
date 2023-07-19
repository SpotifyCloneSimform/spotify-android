package com.simformsolutions.myspotify.data.service

import com.simformsolutions.myspotify.data.model.remote.Album
import com.simformsolutions.myspotify.data.model.remote.Artist
import com.simformsolutions.myspotify.data.model.remote.FollowedArtists
import com.simformsolutions.myspotify.data.model.remote.MediaItems
import com.simformsolutions.myspotify.data.model.remote.Playlist
import com.simformsolutions.myspotify.data.model.remote.SavedAlbum
import com.simformsolutions.myspotify.data.model.remote.Shows
import retrofit2.Response
import retrofit2.http.GET

interface UserLibraryService {

    @GET("me/playlists")
    suspend fun getPlaylists(): Response<MediaItems<Playlist>>

    @GET("me/albums")
    suspend fun getAlbums(): Response<MediaItems<SavedAlbum>>

    @GET("me/following?type=artist")
    suspend fun getFollowedArtists(): Response<FollowedArtists>

    @GET("me/shows")
    suspend fun getSavedShows(): Response<MediaItems<Shows>>
}