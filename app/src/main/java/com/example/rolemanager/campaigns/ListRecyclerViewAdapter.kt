package com.example.rolemanager.campaigns

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rolemanager.databinding.ListCampaignsBinding

class ListRecyclerViewAdapter(private val listItem: List<String>) : RecyclerView.Adapter<ListRecyclerViewAdapter.ListVH>() {

    inner class ListVH(binding: ListCampaignsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.listCampaignTitle
        val image = binding.listCampaignImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListCampaignsBinding.inflate(layoutInflater, parent, false)
        return ListVH(binding)
    }

    override fun onBindViewHolder(holder: ListVH, position: Int) {
        val item = listItem[position]
        holder.name.text = item
    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}