package com.ironclad.notesapp.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.ironclad.notesapp.data.firebase.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: FirebaseRepository
) : ViewModel() {

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    fun signInWithGoogle(account: GoogleSignInAccount) {
        repository.signInWithGoogle(account)
            .addOnSuccessListener {
                _success.postValue(true)
            }.addOnFailureListener {
                _success.postValue(false)
            }
    }
}