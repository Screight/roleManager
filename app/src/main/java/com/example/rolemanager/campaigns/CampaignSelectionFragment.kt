package com.example.rolemanager.campaigns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rolemanager.databinding.FragmentCampaignsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CampaignSelectionFragment : Fragment() {

    private lateinit var binding: FragmentCampaignsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCampaignsBinding.inflate(inflater)

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        val campaignList = ArrayList<CampaignData>()

        val test = db.collection("campaigns").whereEqualTo("ownedID", auth.uid.toString()).get().addOnSuccessListener{ documents ->
            documents.forEach{
                val name = it.get(CampaignData.nameFieldName).toString()
                val description = it.get(CampaignData.descriptionFieldName).toString()
                val hexColor = it.get(CampaignData.backgroundColorFieldName).toString()

                val campaignData = CampaignData(name, description, hexColor)
                campaignList.add(campaignData)
            }

            binding.list.adapter = CampaignListRecyclerViewAdapter(campaignList)

        }

        binding.list.adapter = CampaignListRecyclerViewAdapter(campaignList)

        return binding.root
    }
}