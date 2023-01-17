package com.example.rolemanager.campaigns

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rolemanager.SkillData
import com.example.rolemanager.databinding.ItemListCampaignOverviewSkillBinding

class SkillListRecyclerViewAdapter(private val listItem: List<SkillData>) : RecyclerView.Adapter<SkillListRecyclerViewAdapter.ListVH>() {

    inner class ListVH(binding: ItemListCampaignOverviewSkillBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.skillItemListName
        val modifier = binding.skillItemListModifier
        val bonus = binding.skillItemListBonus
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListCampaignOverviewSkillBinding.inflate(layoutInflater, parent, false)
        return ListVH(binding)
    }

    override fun onBindViewHolder(holder: ListVH, position: Int) {
        val item = listItem[position]

        holder.name.text = item.name
        holder.modifier.text = item.modifier
        holder.bonus.text = "+${item.bonus}"

    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}