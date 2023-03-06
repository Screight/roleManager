package com.example.rolemanager.campaigns

import android.content.Intent
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

        val campaignList = ArrayList<CampaignData>()

        binding.list.adapter = CampaignListRecyclerViewAdapter(campaignList)

        binding.createCampaignButton.setOnClickListener { goToCreateCampaign()  }

        return binding.root
    }

    private fun getListFromDatabase(){
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        val campaignList = ArrayList<CampaignData>()

        db.collection("campaigns").whereEqualTo("ownedID", auth.uid.toString()).get().addOnSuccessListener{ documents ->
            documents.forEach{
                val name = it.get(CampaignData.nameFieldName).toString()
                val description = it.get(CampaignData.descriptionFieldName).toString()
                val hexColor = it.get(CampaignData.backgroundColorFieldName).toString()

                val campaignData = CampaignData(name, description, hexColor)
                campaignList.add(campaignData)
            }

            (binding.list?.adapter as CampaignListRecyclerViewAdapter).updateItemList(campaignList)
        }
    }

    private fun goToCreateCampaign() {
        val intent = Intent(activity, CreateCampaignActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        getListFromDatabase()
    }

}