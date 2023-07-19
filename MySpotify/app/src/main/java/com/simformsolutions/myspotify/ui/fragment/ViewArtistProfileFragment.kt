package com.simformsolutions.myspotify.ui.fragment

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.remote.Artist
import com.simformsolutions.myspotify.databinding.FragmentViewArtistProfileBinding
import com.simformsolutions.myspotify.listener.ItemClickListener
import com.simformsolutions.myspotify.ui.adapter.ArtistTopTrackAdapter
import com.simformsolutions.myspotify.ui.adapter.RelatedArtistAdapter
import com.simformsolutions.myspotify.ui.base.BaseFragment
import com.simformsolutions.myspotify.ui.binding.bindImage
import com.simformsolutions.myspotify.ui.viewmodel.ArtistProfileViewModel
import com.simformsolutions.myspotify.utils.Formatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ViewArtistProfileFragment : BaseFragment<FragmentViewArtistProfileBinding, ArtistProfileViewModel>() {

    override val viewModel: ArtistProfileViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_view_artist_profile

    private val args: ViewArtistProfileFragmentArgs by navArgs()

    private val artistTopTrackAdapter = ArtistTopTrackAdapter()

    private val relatedArtistAdapter = RelatedArtistAdapter()

    override fun initialize() {
        super.initialize()
        setupUI()
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n", "StringFormatMatches")
    override fun initializeObservers() {
        super.initializeObservers()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.artistProfile.collectLatest {  artist ->
                        artist?.let {
                            binding.imgArtist.bindImage(it.images.firstOrNull()?.url ?: "", requireContext().getDrawable(R.drawable.baseline_music_note_24))
                            binding.tvArtistFollowers.text = getString(R.string.followers_formatted, Formatter.formatNumber(artist.followers.total))
                        }
                    }
                }

                launch {
                    viewModel.topTracks.collectLatest { songs ->
                        artistTopTrackAdapter.submitList(songs)
                    }
                }

                launch {
                    viewModel.relatedArtist.collectLatest { relatedArtist ->
                        relatedArtist?.let {
                            relatedArtistAdapter.submitList(it.artist)
                        }
                    }
                }
            }
        }
    }

    private fun setupUI() {
        binding.rvPopularSong.adapter = artistTopTrackAdapter
        binding.rvRelatedArtist.adapter = relatedArtistAdapter
        relatedArtistAdapter.itemClickListener = object : ItemClickListener<Artist> {
            override fun onClick(item: Artist, position: Int) {
                val destination = ViewArtistProfileFragmentDirections.actionViewArtistProfileSelf(item.id)
                findNavController().navigate(destination)
            }
        }
        viewModel.getArtistProfile(args.artistId)
    }

}