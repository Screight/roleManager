package com.example.rolemanager.campaigns

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rolemanager.CampaignActivity
import com.example.rolemanager.databinding.ItemListCampaignsBinding

import com.mikhaellopez.circularimageview.CircularImageView
import org.checkerframework.checker.units.qual.Length


class CampaignListRecyclerViewAdapter(private var listItem: List<CampaignData>) : RecyclerView.Adapter<CampaignListRecyclerViewAdapter.ListVH>() {

    inner class ListVH(binding: ItemListCampaignsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.listCampaignTitle
        val image = binding.listCampaignIcon
        val players = binding.listCampaignPlayers

        init{
            itemView.setOnClickListener {
                val intent = Intent(it.context, CampaignActivity::class.java)
                it.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListCampaignsBinding.inflate(layoutInflater, parent, false)
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

    fun updateItemList(itemList : List<CampaignData>){
        listItem = itemList
        notifyDataSetChanged()
    }

}