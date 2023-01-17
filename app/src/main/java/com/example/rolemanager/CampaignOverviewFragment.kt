package com.example.rolemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rolemanager.campaigns.CampaignData
import com.example.rolemanager.campaigns.CampaignListRecyclerViewAdapter
import com.example.rolemanager.campaigns.SkillListRecyclerViewAdapter
import com.example.rolemanager.databinding.FragmentCampaignOverviewBinding
import com.example.rolemanager.databinding.FragmentCampaignsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CampaignOverviewFragment : Fragment() {
    private lateinit var binding: FragmentCampaignOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCampaignOverviewBinding.inflate(inflater)

        var skillList = listOf<SkillData>(
            SkillData("Skill", "Modifier", 1),
            SkillData("Skill", "Modifier", 1),
            SkillData("Skill", "Modifier", 1)
        )

        binding.campaignOverviewSkillsList.adapter = SkillListRecyclerViewAdapter(skillList)

        return binding.root
    }
}