package com.simformsolutions.myspotify.ui.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.databinding.FragmentNowPlayingBinding
import com.simformsolutions.myspotify.ui.base.BaseFragment
import com.simformsolutions.myspotify.ui.viewmodel.NowPlayingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NowPlayingFragment : BaseFragment<FragmentNowPlayingBinding, NowPlayingViewModel>() {

    private val args: NowPlayingFragmentArgs by navArgs()

    override val viewModel: NowPlayingViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_now_playing

    override fun initialize() {
        super.initialize()
        setupUI()
    }

    private fun setupUI() {
        viewModel.setupPlayingQueue(args.trackId, args.id, args.type)
    }
}