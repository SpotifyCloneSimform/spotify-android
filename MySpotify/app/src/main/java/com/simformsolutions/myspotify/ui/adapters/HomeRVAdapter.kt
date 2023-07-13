package com.simformsolutions.myspotify.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.simformsolutions.myspotify.R
import com.simformsolutions.myspotify.data.model.remote.HomeData
import com.simformsolutions.myspotify.data.model.remote.HomeDisplayData
import com.simformsolutions.myspotify.databinding.ItemHomeRvBinding
import com.simformsolutions.myspotify.ui.base.BaseAdapter

class HomeRVAdapter : BaseAdapter<HomeData>() {

    override fun getLayoutId(viewType: Int): Int = R.layout.item_home_rv

    override fun setDataForListItemWithPosition(
        binding: ViewDataBinding,
        data: HomeData,
        adapterPosition: Int
    ) {
        (binding as? ItemHomeRvBinding)?.apply {
            val adapter = HomeItemRVAdapter()
            title = data.sectionName
            rvHomeData.adapter = adapter
            adapter.submitList(data.homeDisplayData)
        }
    }

}

//class HomeRVAdapter: RecyclerView.Adapter<HomeRVAdapter.ViewHolder>() {
//
//    var homeSections = mutableListOf<HomeData>()
//
//    class ViewHolder(private val binding: ItemHomeRvBinding): RecyclerView.ViewHolder(binding.root) {
//        private  val adapter = HomeItemRVAdapter()
//        fun bind(data: HomeData) {
//            binding.title = data.sectionName
//            binding.rvHomeData.adapter = adapter
//            adapter.submitList(data.homeDisplayData)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(ItemHomeRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//    }
//
//    override fun getItemCount(): Int {
//        return homeSections.count()
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(homeSections[position])
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun submitList(list: List<HomeData>) {
//        homeSections.clear()
//        homeSections.addAll(list)
//        notifyDataSetChanged()
//    }
//}