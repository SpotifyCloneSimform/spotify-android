package com.simformsolutions.myspotify.data.model.local

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.simformsolutions.myspotify.BR
import com.google.gson.annotations.SerializedName
import com.simformsolutions.myspotify.data.model.remote.Image
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowData(
    val id: String?,
    val description: String?,
    val images: String?,
    val name: String?,
    val publisher: String?,
    private var _isExpand: Boolean = false
) : Parcelable, BaseObservable() {

    @get:Bindable
    var isExpand: Boolean = _isExpand
    set(value) {
        _isExpand = value
        field = value
        notifyPropertyChanged(BR.expand)
    }
}