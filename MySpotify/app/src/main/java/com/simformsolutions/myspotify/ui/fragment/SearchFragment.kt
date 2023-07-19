package com.simformsolutions.myspotify.ui.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.remote.Category
import com.simformsolutions.myspotify.databinding.FragmentSearchBinding
import com.simformsolutions.myspotify.listener.ItemClickListener
import com.simformsolutions.myspotify.ui.adapter.CategoryAdapter
import com.simformsolutions.myspotify.ui.base.BaseFragment
import com.simformsolutions.myspotify.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    private lateinit var categoryAdapter: CategoryAdapter

    override val viewModel: SearchViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_search

    override fun initialize() {
        super.initialize()
        setupUI()
    }

    override fun initializeObservers() {
        super.initializeObservers()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.categories.collectLatest { list ->
                    categoryAdapter.submitList(list)
                }
            }
        }
    }

    private fun setupUI() {
        categoryAdapter = CategoryAdapter()
        categoryAdapter.itemClickListener = object : ItemClickListener<Category> {
            override fun onClick(item: Category, position: Int) {
                val destination = SearchFragmentDirections.actionSearchFragmentToCategoryPlaylistFragment(item.id, item.name)
                findNavController().navigate(destination)
            }
        }
        binding.rvCategory.adapter = categoryAdapter
        setupListener()
    }
    
    private fun setupListener() {
        binding.btnSearch.setOnClickListener {
            val destination = SearchFragmentDirections.actionSearchFragmentToSearchHistoryFragment()
            findNavController().navigate(destination)
        }
    }
}