package com.simformsolutions.myspotify.ui.activity

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.databinding.ActivityAuthBinding
import com.simformsolutions.myspotify.ui.base.BaseActivity
import com.simformsolutions.myspotify.ui.viewmodel.AuthViewModel
import com.simformsolutions.myspotify.utils.AppConstants
import com.simformsolutions.myspotify.utils.IntentData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.URL

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding, AuthViewModel>() {

    override val viewModel: AuthViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_auth

    override fun initialize() {
        super.initialize()
        checkAuthorizationStatus()
        setClickListener()
    }

    override fun initializeObservers() {
        super.initializeObservers()
        lifecycleScope.launch {
            launch {
                viewModel.errorMessage.collectLatest { message ->
                    if (message.isNotEmpty()) {
                        Toast.makeText(this@AuthActivity, message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            launch {
                viewModel.authToken.collect { token ->
                    token?.let {
                        loginSuccess()
                    }
                }
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnSignIn -> {
                loadSignInPage()
            }
        }
    }

    private fun loadSignInPage() {
        val authUri = Uri.parse(AppConstants.BASE_AUTH_URL).buildUpon()
            .appendPath("authorize")
            .appendQueryParameter("client_id", AppConstants.CLIENT_ID)
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("redirect_uri", AppConstants.REDIRECT_URI)
            .appendQueryParameter("state", AppConstants.STATE)
            .appendQueryParameter("scope", AppConstants.SCOPES)
            .appendQueryParameter("show_dialog", "true")
            .build()
        openLink(authUri)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.getQueryParameter(IntentData.CODE)?.let { code ->
            viewModel.generateAuthorizationToken(code)
        }
    }

    private fun checkAuthorizationStatus() {
        viewModel.validateLoginStatus { isLoggedIn ->
            if (isLoggedIn) {
                loginSuccess()
            }
        }
    }

    private fun setClickListener() {
        binding.btnSignIn.setOnClickListener(this)
    }

    private fun openLink(uri: Uri) {
        Intent(Intent.ACTION_VIEW, uri).also { intent ->
            startActivity(intent)
        }
    }

    private fun loginSuccess() {
        Intent(this, MainActivity::class.java).also { intent ->
            startActivity(intent)
        }
        finish()
    }
}