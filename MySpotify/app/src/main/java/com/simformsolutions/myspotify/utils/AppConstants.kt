package com.simformsolutions.myspotify.utils

/**
 * Constants used across the application.
 */
object AppConstants {

    /**
     * URLs
     */
    const val BASE_AUTH_URL = "https://accounts.spotify.com/"
    const val BASE_API_URL = "https://api.spotify.com/"

    /**
     * API
     */
    const val API = "api/"
    const val VERSION = "v1/"

    /**
     * Auth
     */
    const val CLIENT_ID = "872e3af2b1bd4d678f3f75c3570e1110"
    const val CLIENT_SECRET = "b1fe4641248b4691b37728b926adc725"
    const val STATE = "AbCdWxYz"
    const val REDIRECT_URI = "myspotify://com.myspotify"
    const val SCOPES = "ugc-image-upload user-read-playback-state user-modify-playback-state user-read-currently-playing app-remote-control streaming playlist-read-private playlist-read-collaborative playlist-modify-private playlist-modify-public user-follow-modify user-follow-read user-read-playback-position user-top-read user-read-recently-played user-library-modify user-library-read user-read-email user-read-private"
}