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

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener{
            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()

            firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signInWithEmailAndPassword(username, password).addOnSuccessListener{
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener{
                Toast.makeText(this, "Incorrect user or password.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}