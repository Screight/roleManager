package com.example.rolemanager.campaigns

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.rolemanager.R
import com.example.rolemanager.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val campaignSelectionFragment = CampaignSelectionFragment()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFragmentTo(campaignSelectionFragment)

        var toolbar : Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Hola"
        setSupportActionBar(toolbar)

    }

    private fun setFragmentTo(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragment.id, fragment)
            commit()
        }
    }

}