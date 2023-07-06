package com.simformsolutions.myspotify.ui.activity

import android.view.View
import androidx.activity.viewModels
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.databinding.ActivityMainBinding
import com.simformsolutions.myspotify.ui.base.BaseActivity
import com.simformsolutions.myspotify.ui.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun onClick(v: View?) {
        // Implement if needed
    }
}