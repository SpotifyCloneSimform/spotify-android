package com.simformsolutions.myspotify.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.simformsolutions.myspotify.helper.PreferenceHelper
import com.simformsolutions.myspotify.ui.base.BaseViewModel
import com.simformsolutions.myspotify.utils.PreferenceKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferenceHelper: PreferenceHelper
) : BaseViewModel() {

    private val _subtitle = MutableStateFlow("")
    val subtitle = _subtitle.asStateFlow()

    private val _toolbarColor = MutableStateFlow<Int?>(null)
    val toolbarColor = _toolbarColor.asStateFlow()

    fun setSubtitle(subtitle: String) {
        viewModelScope.launch {
            _subtitle.emit(subtitle)
        }
    }

    fun updateToolbarColor(color: Int?) {
        viewModelScope.launch {
            _toolbarColor.emit(color)
        }
    }

    fun validateLoginStatus(onComplete: (Boolean) -> Unit) {
        preferenceHelper.getString(PreferenceKeys.ACCESS_TOKEN, "").let { token ->
            onComplete(token.isNotEmpty())
        }
    }
}