package com.simformsolutions.myspotify.ui.activity

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.databinding.ActivityMainBinding
import com.simformsolutions.myspotify.extentions.getThemeColor
import com.simformsolutions.myspotify.ui.base.BaseActivity
import com.simformsolutions.myspotify.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var navController: NavController

    override val viewModel: MainViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initialize() {
        super.initialize()
        checkLoginStatus()
        setupUI()
    }

    override fun initializeObservers() {
        super.initializeObservers()
        lifecycleScope.launch {
            launch {
                viewModel.subtitle.collectLatest { subtitle ->
                    binding.toolbar.subtitle = subtitle
                }
            }
            launch {
                viewModel.toolbarColor.collectLatest { color ->
                    binding.toolbar.setBackgroundColor(
                        color ?: getThemeColor(com.google.android.material.R.attr.backgroundColor)
                    )
                }
            }
            launch {
                viewModel.appBarScrollingEnabled.collectLatest { isEnabled ->
                    setAppBarScrolling(isEnabled)
                }
            }
        }
    }

    override fun onClick(v: View) {
        // Implement when needed
    }

    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        (supportFragmentManager.findFragmentById(binding.hostFragmentContainer.id) as? NavHostFragment)?.let {
            navController = it.navController
        }
        setupNavigation()
    }

    // Setup and sync bottom navigation and app bar with fragment.
    private fun setupNavigation() {
        binding.toolbar.setupWithNavController(navController)
        binding.bottomNav.setupWithNavController(navController)
    }

    private fun setAppBarScrolling(isEnable: Boolean) {
        binding.toolbar.updateLayoutParams<AppBarLayout.LayoutParams> {
            scrollFlags =
                if (isEnable) {
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                            AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS or
                            AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
                } else {
                    binding.toolbar.scrollY = 0
                    AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
                }
        }
    }

    private fun checkLoginStatus() {
        viewModel.validateLoginStatus { isLoggedIn ->
            if (!isLoggedIn) {
                logout()
            }
        }
    }

    private fun logout() {
        Intent(this, AuthActivity::class.java).also { intent ->
            startActivity(intent)
        }
        finish()
    }
}