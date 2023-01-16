package com.example.rolemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rolemanager.campaigns.HomeActivity
import com.example.rolemanager.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null) {
            Toast.makeText(
                this,
                "Logged in as ${firebaseAuth.currentUser?.email}",
                Toast.LENGTH_SHORT
            ).show()
            login()
        }

        binding.loginButton.setOnClickListener {
            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()

            firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnSuccessListener {
                    login()
                }.addOnFailureListener {
                    Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT)
                        .show()
                }
        }

        binding.registerButton.setOnClickListener {
            goToRegisterActivity()
        }

    }

    private fun login() {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun goToRegisterActivity(){
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        firebaseAuth.signOut()
    }
}