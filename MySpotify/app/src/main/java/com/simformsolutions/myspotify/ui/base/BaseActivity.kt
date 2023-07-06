package com.simformsolutions.myspotify.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.simformsolutions.myspotify.BR

abstract class BaseActivity<Binding : ViewDataBinding, ViewModel : androidx.lifecycle.ViewModel> :
    AppCompatActivity(), View.OnClickListener {

    protected lateinit var binding: Binding
    protected abstract val viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTheme()
        bindViewModel()
        initializeObservers()
    }

    @LayoutRes
    abstract fun getLayoutResId(): Int

    private fun bindViewModel() {
        binding = DataBindingUtil.setContentView(this, getLayoutResId())
        binding.apply {
            lifecycleOwner = this@BaseActivity
            setVariable(BR.viewModel, viewModel)
        }
        binding.executePendingBindings()
        initialize()
    }

    open fun initializeObservers() {}

    open fun initialize() {}

    private fun setupTheme() {
        // Set dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}