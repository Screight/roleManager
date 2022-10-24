package com.example.rolemanager.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rolemanager.databinding.ItemListBinding

class ListRecyclerViewAdapter(private val listItem: List<String>) : RecyclerView.Adapter<ListRecyclerViewAdapter.ListVH>() {

    inner class ListVH(binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.userText
        val image = binding.userImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(layoutInflater)
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