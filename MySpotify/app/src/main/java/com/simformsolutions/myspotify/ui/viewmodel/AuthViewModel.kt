package com.simformsolutions.myspotify.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.simformsolutions.myspotify.data.model.GrantType
import com.simformsolutions.myspotify.data.repository.AuthRepository
import com.simformsolutions.myspotify.helper.PreferenceHelper
import com.simformsolutions.myspotify.ui.base.BaseViewModel
import com.simformsolutions.myspotify.utils.AppConstants
import com.simformsolutions.myspotify.utils.PreferenceKeys
import com.simformsolutions.myspotify.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferenceHelper: PreferenceHelper
) : BaseViewModel() {

    private val _authToken = MutableStateFlow<String?>(null)
    val authToken = _authToken.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    fun generateAuthorizationToken(code: String) {
        viewModelScope.launch {
            val body = hashMapOf(
                "grant_type" to GrantType.AUTHORIZATION_CODE.toString(),
                "code" to code,
                "redirect_uri" to AppConstants.REDIRECT_URI
            )

            authRepository.getAuthorizationToken(body).collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }

                    is Resource.Success -> {
                        resource.data?.let { data ->
                            preferenceHelper.apply {
                                putBoolean(PreferenceKeys.IS_LOGGED_IN, true)
                                putString(PreferenceKeys.ACCESS_TOKEN, data.accessToken)
                                putString(PreferenceKeys.REFRESH_TOKEN, data.refreshToken)
                                putInt(PreferenceKeys.EXPIRES_AT, Calendar.getInstance().get(Calendar.SECOND) + data.expireTime)
                            }
                        }
                        _isLoading.emit(false)
                        _authToken.emit(resource.data?.accessToken)
                    }

                    is Resource.Error -> {
                        _isLoading.emit(false)
                        resource.message?.let { _errorMessage.emit(it) }
                    }
                }
            }
        }
    }

    fun validateLoginStatus(onComplete: (Boolean) -> Unit) {
        onComplete(preferenceHelper.getBoolean(PreferenceKeys.IS_LOGGED_IN, false))
    }
}