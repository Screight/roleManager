package com.example.rolemanager.campaigns

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rolemanager.databinding.ActivityCampaignsBinding

class CampaignSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCampaignsBinding

    private val lista = listOf(
        "Save the Shire",
        "The Black Gate Opens",
        "Battle of the Pelennor Fields"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide();

        binding = ActivityCampaignsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.list.adapter = ListRecyclerViewAdapter(lista)
    }
}