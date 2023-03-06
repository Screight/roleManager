package com.example.rolemanager.login

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    var isLoggedIn = MutableLiveData<Boolean>()
    private val firebaseAuth = FirebaseAuth.getInstance()

    init {
        if(firebaseAuth.currentUser != null){
            isLoggedIn.postValue(true)
        }
    }

    fun loginWithEmail(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                isLoggedIn.postValue(true)
            }.addOnFailureListener {
                isLoggedIn.postValue(false)
            }
    }

    fun getEmail() : String? { return firebaseAuth.currentUser?.email }

    fun signOut() { firebaseAuth.signOut() }

}