package com.example.rolemanager.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rolemanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val lista = listOf(
        "uno",
        "uno",
        "uno",
        "uno",
        "uno",
        "uno"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.list.adapter = ListRecyclerViewAdapter(lista)
    }
}