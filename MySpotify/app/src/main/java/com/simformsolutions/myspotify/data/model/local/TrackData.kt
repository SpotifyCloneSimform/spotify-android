package com.simformsolutions.myspotify.data.model.local

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.simformsolutions.myspotify.BR

data class TrackItem(
    val id: String,
    val image: String?,
    val title: String,
    val type: String,
    val artists: String,
    var description: String = "",
    private var _isLiked: Boolean = false
) : BaseObservable() {

    @get:Bindable
    var isLiked: Boolean = _isLiked
        set(value) {
            _isLiked = value
            field = value
            notifyPropertyChanged(BR.liked)
        }
}