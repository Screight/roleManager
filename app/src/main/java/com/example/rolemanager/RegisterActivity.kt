package com.example.rolemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rolemanager.campaigns.HomeActivity
import com.example.rolemanager.databinding.ActivityLoginBinding
import com.example.rolemanager.databinding.ActivityRegisterBinding
import com.example.rolemanager.list.MainActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = resources.getString(R.string.register_form_title)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun goToLoginActivity(){
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)

        finish()
    }
}