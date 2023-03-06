package com.example.rolemanager

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.rolemanager.databinding.ActivityCreateCampaignBinding
import com.example.rolemanager.databinding.ActivityPermisionClassBinding

class PermissionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPermisionClassBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPermisionClassBinding.inflate(layoutInflater)



        val activityForResultLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { RESULT ->
                binding.image.setImageBitmap(RESULT)
            }


        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->

                if (isGranted)
                    activityForResultLauncher.launch(null)
                else
                    Toast.makeText(this, "meh", Toast.LENGTH_LONG)

            }


        binding.button.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,  Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                // Ja tinc el permís
                activityForResultLauncher.launch(null)
            } else {
                // No tinc el permís i l'he de sol·licitar
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }

        setContentView(binding.root)
    }
}