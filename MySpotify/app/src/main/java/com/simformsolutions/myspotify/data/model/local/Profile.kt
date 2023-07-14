package com.simformsolutions.myspotify.data.model.local

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.simformsolutions.myspotify.BR

data class ProfileMediaItem(
    val image: String?,
    val title: String,
    private var _artists: String? = null,
    private var _followers: Int = 0,
) : BaseObservable() {

    @get:Bindable
    var artists: String? = _artists
        set(value) {
            _artists = value
            field = value
            notifyPropertyChanged(BR.artists)
        }

    @get:Bindable
    var followers: Int = _followers
        set(value) {
            _followers = value
            field = value
            notifyPropertyChanged(BR.followers)
        }
}