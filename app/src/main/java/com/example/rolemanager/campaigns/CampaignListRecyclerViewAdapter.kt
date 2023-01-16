package com.example.rolemanager.campaigns

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rolemanager.databinding.ListCampaignsBinding

import com.mikhaellopez.circularimageview.CircularImageView


class CampaignListRecyclerViewAdapter(private val listItem: List<CampaignData>) : RecyclerView.Adapter<CampaignListRecyclerViewAdapter.ListVH>() {

    inner class ListVH(binding: ListCampaignsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.listCampaignTitle
        val image = binding.listCampaignIcon
        val players = binding.listCampaignPlayers
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListCampaignsBinding.inflate(layoutInflater, parent, false)
        return ListVH(binding)
    }

    override fun onBindViewHolder(holder: ListVH, position: Int) {
        val item = listItem[position]
        holder.name.text = item.name
        holder.players.text = "Provisional"

        val color : Int = try{
            Color.parseColor("#${item.backgroundColorHex}")
        } catch ( e : IllegalArgumentException){
            1
        }

        holder.image.apply {
            circleColor = color
        }

    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}