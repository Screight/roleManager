package com.example.rolemanager.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.rolemanager.RegisterActivity
import com.example.rolemanager.campaigns.HomeActivity
import com.example.rolemanager.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel.isLoggedIn.observe(this) {

            if(it == true){
                Toast.makeText(
                    this,
                    "Logged in as "+ loginViewModel.getEmail(),
                    Toast.LENGTH_SHORT
                ).show()
                goToHomeActivity()
            }
            else{
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.loginButton.setOnClickListener {
            val email = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()

            loginViewModel.loginWithEmail(email, password)
        }

        binding.registerButton.setOnClickListener {
            goToRegisterActivity()
        }

    }

    private fun goToHomeActivity() {
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
        loginViewModel.signOut()
    }
}