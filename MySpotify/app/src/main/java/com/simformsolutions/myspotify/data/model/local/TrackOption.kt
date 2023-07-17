package com.simformsolutions.myspotify.data.model.local

import androidx.annotation.DrawableRes
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.simformsolutions.myspotify.BR

data class TrackOptionItem(
    @DrawableRes private var _icon: Int,
    private var _title: String,
    var onClick: (() -> Unit)? = null
): BaseObservable() {

    @DrawableRes
    @get:Bindable
    var icon: Int = _icon
        set(value) {
            _icon = value
            field = value
            notifyPropertyChanged(BR.icon)
        }

    @get:Bindable
    var title: String = _title
        set(value) {
            _title = value
            field = value
            notifyPropertyChanged(BR.title)
        }
}