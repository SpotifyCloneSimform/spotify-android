package com.simformsolutions.myspotify.ui.fragment

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.local.ItemType
import com.simformsolutions.myspotify.databinding.FragmentNowPlayingBinding
import com.simformsolutions.myspotify.ui.base.BaseFragment
import com.simformsolutions.myspotify.ui.viewmodel.MainViewModel
import com.simformsolutions.myspotify.ui.viewmodel.NowPlayingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NowPlayingFragment : BaseFragment<FragmentNowPlayingBinding, NowPlayingViewModel>() {

    private val args: NowPlayingFragmentArgs by navArgs()
    private val activityViewModel: MainViewModel by activityViewModels()

    override val viewModel: NowPlayingViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_now_playing

    override fun initialize() {
        super.initialize()
        setupUI()
    }

    override fun initializeObservers() {
        super.initializeObservers()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.name.collectLatest { name ->
                    activityViewModel.setSubtitle(name)
                }
            }
        }
    }

    override fun onDestroyView() {
        activityViewModel.setAppBarScrollingEnabled(true)
        activityViewModel.setSubtitle("")
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        super.onDestroyView()
    }


    @SuppressLint("SourceLockedOrientationActivity")
    private fun setupUI() {
        activityViewModel.setAppBarScrollingEnabled(false)
        setupTitle()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        viewModel.setupPlayingQueue(args.trackId, args.id, args.type)
    }

    private fun setupTitle() {
        val type = if (args.type == ItemType.TRACK) {
            ItemType.ALBUM
        } else {
            args.type
        }
        requireActivity().title =
            getString(R.string.playing_from, type.getLocalizedName(requireContext()))
    }
}