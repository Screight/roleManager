package com.example.rolemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.core.widget.doOnTextChanged
import com.example.rolemanager.databinding.ActivityRegisterBinding
import com.example.rolemanager.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        val actionBar = supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = resources.getString(R.string.register_form_title)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textNameField.doOnTextChanged { name, _, _, _ ->
            checkName(name.toString())
        }

        binding.textEmailField.doOnTextChanged{emailInput, _, _, _ ->
            checkEmailAdress(emailInput.toString())
        }

        binding.textPasswordField.doOnTextChanged{_, _, _, _ ->
            checkIfPasswordsMatch()
        }

        binding.textRepeatPasswordField.doOnTextChanged{_, _, _, _ ->
            checkIfPasswordsMatch()
        }

        binding.registerButton.setOnClickListener{
            var isValid = true

            isValid = checkName(binding.textNameField.text.toString())

            if(isValid)
                isValid = checkPasswordPattern(binding.textPasswordField.text.toString())
            if(isValid)
                isValid = checkIfPasswordsMatch()
            if(isValid)
                isValid = checkEmailAdress(binding.textEmailField.text.toString())

            if(isValid)
                register(binding.textEmailField.text.toString(), binding.textPasswordField.text.toString())
        }
    }

    private fun checkName(name : String) : Boolean{
        var minNumberOfChar = resources.getInteger(R.integer.register_form_min_number_name_char)
        var maxNumberOfChar = resources.getInteger(R.integer.register_form_max_number_name_char)

        val numberOfChars = name?.length ?: 0

        if(numberOfChars < minNumberOfChar){
            var message = getString(R.string.register_form_name_min_chars)
            message = message.replace("{i}", minNumberOfChar.toString())
            binding.textNameInputLayout.error = message
            return false
        }else if(numberOfChars > maxNumberOfChar){
            var message = getString(R.string.register_form_name_max_chars)
            message = message.replace("{i}", maxNumberOfChar.toString())
            binding.textNameInputLayout.error = message
            return false
        }
        else{
            binding.textNameInputLayout.error = null
            binding.textNameInputLayout.helperText = getString(R.string.form_default_valid)
            return true
        }
    }

    private fun checkPasswordPattern(password : String) : Boolean{
        var pattern : Pattern
        var matcher : Matcher

        var isValid = true

        // Check for letters
        pattern = Pattern.compile("[a-zA-Z]")
        matcher = pattern.matcher(password)
        if(!matcher.find()){
            binding.textPasswordInputLayout.error = getString(R.string.register_form_password_letter)
            isValid = false
        }

        if(isValid){
            // Check for numbers
            pattern = Pattern.compile("[0-9]")
            matcher = pattern.matcher(password)
            if(!matcher.find()){
                binding.textPasswordInputLayout.error = getString(R.string.register_form_password_number)
                isValid = false
            }
        }
        5
        if(!isValid && password?.length == 0)
            binding.textPasswordInputLayout.helperText = getString(R.string.register_form_required)

        if(isValid){
            binding.textPasswordInputLayout.error = null
            binding.textPasswordInputLayout.helperText = getString(R.string.form_default_valid)
        }

        return isValid
    }

    private fun checkIfPasswordsMatch() : Boolean{
        if(binding.textPasswordField.text.toString()?.equals(binding.textRepeatPasswordField.text.toString())){
            binding.textRepeatPasswordInputLayout.error = null
            binding.textRepeatPasswordInputLayout.helperText = getString(R.string.form_default_valid)
            return true
        }
        else
            binding.textRepeatPasswordInputLayout.error = getString(R.string.register_form_password_unmatch)
        return false
    }

    private fun checkEmailAdress(emailInput : String) : Boolean{
        if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            binding.textEmailInputLayout.error = getString(R.string.register_form_email_invalid)
            return false
        }
        else{
            binding.textEmailInputLayout.error = null
            return true
        }
    }

    private fun register(email : String, password : String){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            if(it.isSuccessful){
                val db = FirebaseFirestore.getInstance()

                val name = binding.textNameField.text.toString()
                val email = binding.textEmailField.text.toString()

                db.collection("users").document(firebaseAuth.currentUser?.uid.toString()).set(
                    hashMapOf(  "name" to name,
                                "email" to email
                    )
                ).addOnSuccessListener {
                    goToLogin()
                }
            }
            else
                binding.textEmailInputLayout.error = getString(R.string.register_form_email_used)
        }
    }

    private fun goToLogin() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)

        finish()
    }
}