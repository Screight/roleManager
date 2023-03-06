package com.example.rolemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.rolemanager.databinding.ActivityCampaignBinding
import com.google.firebase.auth.FirebaseAuth

class CampaignActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCampaignBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityCampaignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val campaignSelectionFragment = CampaignOverviewFragment()
        setFragmentTo(campaignSelectionFragment)
    }

    private fun setFragmentTo(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragment.id, fragment)
            commit()
        }
    }

}