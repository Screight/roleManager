package com.example.rolemanager.campaigns

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rolemanager.databinding.FragmentCampaignsBinding
import com.google.firebase.ktx.Firebase

class CampaignSelectionFragment : Fragment() {

    private lateinit var binding: FragmentCampaignsBinding

    private val lista = listOf(
        "Save the Shire",
        "The Black Gate Opens",
        "Battle of the Pelennor Fields"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCampaignsBinding.inflate(inflater)

        binding.list.adapter = ListRecyclerViewAdapter(lista)

        return binding.root
    }
}