package com.example.rolemanager.campaigns

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.rolemanager.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private val lista = listOf(
        "Save the Shire",
        "The Black Gate Opens",
        "Battle of the Pelennor Fields"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide();

        val campaignSelectionFragment = CampaignSelectionFragment()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFragmentTo(campaignSelectionFragment)
    }

    private fun setFragmentTo(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragment.id, fragment)
            commit()
        }
    }

}